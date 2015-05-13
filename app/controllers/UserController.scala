package controllers

import play.api._
import play.api.http._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._

import play.api.db.slick._
import scala.slick.driver.PostgresDriver.simple._
import models._

case class UserForm(email: String, password: String)

object UserController extends Controller {

	val users = TableQuery[Users]

	val userForm = Form(
		mapping(
			"email" -> email,
			"password" -> nonEmptyText
			)(UserForm.apply)(UserForm.unapply))

	def signup = DBAction { implicit request =>

		val form = userForm.bindFromRequest

		if (form.hasErrors) {
			Status(520)("Not an email format")
		}
		else {
			val data = form.get

			if (0 < users.filter(_.email === data.email).length.run) {
				Status(530)("Email already exists.")
			}
			else {
				users += User(data.email, data.password)
				Ok("Welcome to Expense Tracker")
			}
		}
	}

	def login = DBAction { implicit request =>

		val form = userForm.bindFromRequest

		if (form.hasErrors) {
			Status(520)("Not an email format")
		}
		else {
			val data = form.get

			if (0 < users.filter(x => x.email === data.email && x.password === data.password).length.run) {
				Ok("good to go").withHeaders(HeaderNames.AUTHORIZATION -> data.email)
			}
			else {
				Status(540)("Wrong email or password.")
			}
		}
	}
}
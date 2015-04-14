package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._

import play.api.db.slick._
import scala.slick.driver.PostgresDriver.simple._
import models._

case class UserForm(email: String, password1: String, password2: String)

object UserController extends Controller {

	val users = TableQuery[Users]

	val userForm = Form(
		mapping(
			"email" -> email,
			"password1" -> nonEmptyText,
			"password2" -> nonEmptyText
		)(UserForm.apply)(UserForm.unapply))

	def create = DBAction { implicit rs =>

		val form = userForm.bindFromRequest
		
		if (form.hasErrors) {
			Status(520)("Not an email format")
		}
		else {
			val data = form.get

			if (data.password1 == data.password2) {

				if (0 < users.filter(_.email === data.email).length.run)
					Status(530)("Email already exists.")
				else {
					users += User(data.email, data.password1)
					Ok("Welcome to Expense Tracker")
				}
			}
			else {
				Status(510)("Password is not matching")
			}
		}
	}
}
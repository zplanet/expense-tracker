package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._

import play.api.db.slick._
import scala.slick.driver.PostgresDriver.simple._
import models._

object ExpenseController extends Controller {

	val users = TableQuery[Users]
	val expenses = TableQuery[Expenses]

	val expenseForm = Form(
		mapping(
			"amount" -> number,
			"date" -> nonEmptyText,
			"password2" -> nonEmptyText
			)(UserForm.apply)(UserForm.unapply))

	val loginForm = Form(
		mapping(
			"email" -> email,
			"password" -> nonEmptyText,
			"id" -> optional(number)
			)(User.apply)(User.unapply))

	def create = DBAction { implicit request =>

		val form = signupForm.bindFromRequest
		
		if (form.hasErrors) {
			Status(520)("Not an email format")
		}
		else {
			val data = form.get

			if (data.password1 == data.password2) {

				if (0 < users.filter(_.email === data.email).length.run) {
					Status(530)("Email already exists.")
				}
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

	def check = DBAction { implicit request =>

		val form = loginForm.bindFromRequest

		if (form.hasErrors) {
			Status(520)("Not an email format")
		}
		else {
			val data = form.get

			if (0 < users.filter(x => x.email === data.email && x.password === data.password).length.run) {
				Ok("user=" + data.email).withCookies(Cookie("user", data.email))
			}
			else {
				Status(540)("Wrong email or password.")
			}
		}
	}
}
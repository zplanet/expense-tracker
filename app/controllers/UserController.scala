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

	def check(f: (String, String) => Result)(implicit request: DBSessionRequest[AnyContent]) = {

		val form = userForm.bindFromRequest

		if (form.hasErrors) {
			Status(520)("Not an email format")
		}
		else {
			val data = form.get
			f(data.email, data.password)
		}
	}

	def signup = DBAction { implicit request =>
		
		check { (email, password) => 			
			if (0 < users.filter(_.email === email).length.run) {
				Status(530)("Email already exists.")
			}
			else {
				users += User(email, password)
				Ok("Welcome to Expense Tracker")
			}
		}
	}

	def login = DBAction { implicit request =>

		check { (email, password) => 
			if (0 < users.filter(x => x.email === email && x.password === password).length.run) {
				Ok("good to go").withHeaders(HeaderNames.AUTHORIZATION -> email)
			}
			else {
				Status(540)("Wrong email or password.")
			}
		}
	}
}
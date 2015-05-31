package controllers

import play.api._
import play.api.http._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._

import play.api.db.slick._
import scala.slick.driver.PostgresDriver.simple._
import models._
import common._

case class UserForm(email: String, password: String)

object UserController extends Controller {

	val users = TableQuery[Users]

	val userForm = Form(
		mapping(
			"email" -> email,
			"password" -> nonEmptyText
		)(UserForm.apply)(UserForm.unapply))

	def checkForm(success: (String, String) => DBSessionRequest[AnyContent] => Result)(implicit maybeApp: MaybeApplication) = DBAction { implicit request =>

		val form = userForm.bindFromRequest

		if (form.hasErrors) {
			Status(520)("Not an email format")
		}
		else {
			val data = form.get
			success(data.email, data.password)(request)
		}
	}

	def signup = checkForm { (email, password) => implicit session =>

		if (0 < users.filter(_.email === email).length.run) {
			Status(530)("Email already exists.")
		}
		else {
			val salt = Util.uuid()
			users += User(email, Util.sha256(salt + Util.sha256(password)), salt)
			Ok("Welcome to Expense Tracker")
		}
	}

	def login = checkForm { (email, password) => implicit session => 

		users.filter(_.email === email).firstOption match {
			case Some(user)	=> {
				if (Util.sha256(user.passwordSalt + password) == user.password) { Ok(email) }
				else { Status(540)("Wrong email or password.") }
			}
			case None 		=> Status(540)("Wrong email or password.")
		}
	}
}
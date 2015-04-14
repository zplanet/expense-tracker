package controllers

import play.api._
import play.api.mvc._

import play.api.db.slick._
import scala.slick.driver.PostgresDriver.simple._
import models._

object Application extends Controller {

	val users = TableQuery[Users]
	val expenses = TableQuery[Expenses]

	def index = Action {
		Ok(views.html.index())
	}

	def signup = Action {
		Ok(views.html.signup())
	}

	def test = DBAction { implicit rs =>
		Ok(views.html.test(users.list, expenses.list))
	}
}
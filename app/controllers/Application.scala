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

	def checkCookie(f:Cookie => Result) = Action { implicit request =>
		request.cookies.get("user") match {
			case Some(c) => f(c)
			case None => Redirect(routes.Application.index)
		}
	}

	def expense = checkCookie(cookie => Ok(views.html.expense(cookie.value)))

	def logout = Action {
		Ok("Bye").discardingCookies(DiscardingCookie("user"))
	}

	def test = DBAction { implicit request =>
		request.cookies.get("user") match {
			case Some(x) => Logger.info(x.value)
			case None => Logger.info("no cookie")
		}
		Ok(views.html.test(users.list, expenses.list))
	}
}
package controllers

import play.api._
import play.api.mvc._

object Application extends Controller {

	def index = Action { implicit request =>
		request.cookies.get("user") match {
			case Some(c)	=> Ok(views.html.index(c.value))
			case None		=> Ok(views.html.index(""))
		}
	}

	def signup = Action {
		Ok(views.html.signup())
	}

	def checkCookie(f:Cookie => Result) = Action { implicit request =>
		request.cookies.get("user") match {
			case Some(c)	=> f(c)
			case None		=> Redirect(routes.Application.index)
		}
	}

	def expense = checkCookie(cookie => Ok(views.html.expense(cookie.value)))

	def logout = Action {
		Redirect(routes.Application.index).discardingCookies(DiscardingCookie("user"))
	}
}
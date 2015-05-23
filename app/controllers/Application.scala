package controllers

import play.api._
import play.api.mvc._

object Application extends Controller {

	def checkCookie(success: Cookie => Result, fail: () => Result) = Action { implicit request =>
		request.cookies.get("user") match {
			case Some(c)	=> success(c)
			case None		=> fail()
		}
	}

	def index	= checkCookie(cookie => Ok(views.html.index(cookie.value)), () => Ok(views.html.index("")))
	def expense	= checkCookie(cookie => Ok(views.html.expense(cookie.value)), () => Redirect(routes.Application.index))
	def report	= checkCookie(cookie => Ok(views.html.report(cookie.value)), () => Redirect(routes.Application.index))
	def graph	= checkCookie(cookie => Ok(views.html.graph(cookie.value)), () => Redirect(routes.Application.index))

	def main = Action { Ok(views.html.main() )}
	
	def signup = Action { Ok(views.html.signup()) }

	def logout = Action { Redirect(routes.Application.index).discardingCookies(DiscardingCookie("user")) }
}
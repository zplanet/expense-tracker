package controllers

import play.api._
import play.api.mvc._
import play.api.http._

object Application extends Controller {
	def index	= Action { Ok(views.html.index()) }
}
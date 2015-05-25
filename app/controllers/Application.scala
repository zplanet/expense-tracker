package controllers

import play.api._
import play.api.mvc._
import play.api.http._

object Application extends Controller {
	def index	= Action { Ok(views.html.index()) }
	def main 	= Action { Ok(views.html.main()) }
	def signup	= Action { Ok(views.html.signup()) }
	def expense	= Action { Ok(views.html.expense()) }
	def report	= Action { Ok(views.html.report()) }
	def graph	= Action { Ok(views.html.graph()) }
}
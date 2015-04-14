package controllers

import play.api._
import play.api.mvc._

import play.api.db.slick._
//import play.api.db.slick.Config.driver.simple._
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
		//Logger.info(Play.current.configuration.getString("db.default.driver").get)
		//Logger.info(Play.current.configuration.getString("db.default.url").get)
		//Logger.info(Play.current.configuration.getString("db.default.user").get)
		//Logger.info(Play.current.configuration.getString("db.default.password").get)
//web: target/universal/stage/bin/expense -Dhttp.port=${PORT} -DapplyEvolutions.default=true -Ddb.default.driver=org.postgresql.Driver -Ddb.default.url=${DATABASE_URL} -Ddb.default.user=${DB_USER} -Ddb.default.password=${DB_PW}

		users += User("abc@abc.com", "a")
		//expenses += Expense(100.0, "2015-04-15", "haha")
		Ok(views.html.test(users.list, expenses.list))
	}
}
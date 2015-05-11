package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._

import play.api.db.slick._
import scala.slick.driver.PostgresDriver.simple._
import models._
import play.api.libs.json._

case class ExpenseForm(amount: BigDecimal, date: java.util.Date, note: String)

object ExpenseController extends Controller {

	val expenses = TableQuery[Expenses]

	implicit val expenseWrites = new Writes[Expense] {
	  def writes(exp: Expense) = Json.obj(
	  	"date"		-> exp.date.toString,
	    "amount"	-> exp.amount,
	    "note"		-> exp.description
	  )
	}

	val expenseForm = Form(
		mapping(
			"amount" -> bigDecimal(20, 2),
			"date" -> date("yyyy-MM-dd"),
			"note" -> nonEmptyText
			)(ExpenseForm.apply)(ExpenseForm.unapply))

	def create = DBAction { implicit request =>
		
		val form = expenseForm.bindFromRequest

		if (form.hasErrors) {
			Status(502)("Please, Check your input.")
		}
		else {
			val data = form.get
			expenses += Expense(data.amount.toDouble, new java.sql.Date(data.date.getTime()), data.note)
			Ok("Data is added successfully.")
		}
	}

	def getAll = DBAction { implicit request =>
		Ok(Json.toJson(expenses.sortBy(_.date.desc).list))
	}

	def getMonthlyData = DBAction { implicit request =>
		//Ok(Json.toJson(expenses.sortBy(_.date.desc).list))
		Ok("")
	}
}
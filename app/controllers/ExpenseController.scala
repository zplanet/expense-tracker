package controllers

import play.api._
import play.api.http._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._

import play.api.db.slick._
import scala.slick.driver.PostgresDriver.simple._
import models._
import play.api.libs.json._

case class ExpenseForm(amount: BigDecimal, date: String, note: String)
case class GroupedData(label: String, total: Option[Double])

object ExpenseController extends Controller {

	val expenses = TableQuery[Expenses]

	implicit val expenseWrites = new Writes[Expense] {
	  def writes(exp: Expense) = Json.obj(
	  	"date"		-> exp.date,
	    "amount"	-> exp.amount,
	    "note"		-> exp.description
	  )
	}

	implicit val reportWrites = new Writes[(String, Double)] {
		def writes(gd: (String, Double)) = Json.arr(
			gd._1, gd._2
		)
	}

	val expenseForm = Form(
		mapping(
			"amount" -> bigDecimal(20, 2),
			"date" -> nonEmptyText,
			"note" -> nonEmptyText
			)(ExpenseForm.apply)(ExpenseForm.unapply))

	def checkAuthorization(success: DBSessionRequest[AnyContent] => Result)(implicit maybeApp: MaybeApplication) = DBAction { implicit request =>
		request.headers.get(HeaderNames.AUTHORIZATION) match {
			case Some(x)	=> success(request)
			case None 		=> Unauthorized("fail to get data")
		}
	}

	def create = checkAuthorization { implicit request =>
		
		val form = expenseForm.bindFromRequest

		if (form.hasErrors) {
			Status(502)("Please, Check your input.")
		}
		else {
			val data = form.get
			expenses += Expense(data.amount.toDouble, data.date, data.note)
			Ok("Data is added successfully.")
		}
	}

	def getAll = checkAuthorization { implicit request => Ok(Json.toJson(expenses.sortBy(_.date.desc).list)) }

	def getMonthlyData4Graph = checkAuthorization { implicit request =>
		val xs = 
			expenses
			.groupBy(e => e.date.substring(0, 7))
			.map { case (date, group) => (date, group.map(_.amount).sum.get) }.list
			.sortBy (_._1)

		Ok(JsArray(Seq(Json.toJson(xs))))
	}
}
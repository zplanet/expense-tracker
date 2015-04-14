package models

import scala.slick.driver.PostgresDriver.simple._

case class Expense(amount: Double, date: java.sql.Date, description: String, id: Option[Int] = None)

class Expenses(tag: Tag) extends Table[Expense](tag, "EXPENSES") {
	def id = column[Int]("ID", O.PrimaryKey, O.AutoInc)
	def amount = column[Double]("AMOUNT", O.NotNull)
	def date = column[java.sql.Date]("DATE", O.NotNull)
	def description = column[String]("DESCRIPTION")
	def * = (amount, date, description, id.?) <> (Expense.tupled, Expense.unapply)
}
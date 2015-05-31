package models

import scala.slick.driver.PostgresDriver.simple._

case class User(email: String, password: String, passwordSalt: String, id: Option[Int] = None)

class Users(tag: Tag) extends Table[User](tag, "USERS") {
	def id = column[Int]("ID", O.PrimaryKey, O.AutoInc)
	def email = column[String]("EMAIL", O.NotNull)
	def password = column[String]("PASSWORD", O.NotNull)
	def passwordSalt = column[String]("PASSWORDSALT", O.NotNull)
	def * = (email, password, passwordSalt, id.?) <> (User.tupled, User.unapply)
}
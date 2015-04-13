package models

import play.api.db.slick.Config.driver.simple._

case class User(email: String, password: String, id: Option[Int] = None)

class Users(tag: Tag) extends Table[User](tag, "USERS") {
	def id = column[Int]("ID", O.PrimaryKey, O.AutoInc)
	def email = column[String]("EMAIL", O.NotNull)
	def password = column[String]("PASSWORD", O.NotNull)
	def * = (email, password, id.?) <> (User.tupled, User.unapply)
}
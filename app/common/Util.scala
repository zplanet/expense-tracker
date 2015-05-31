package common

import java.security.MessageDigest
import java.util.UUID
import play.api.libs._

object Util {
	def sha256(msg: String) = {
		val md = MessageDigest.getInstance("SHA-256")
		md.update(msg.getBytes())
		(md.digest() map (x => "%02x" format (x & 0xff))).mkString("")
	}

	def uuid() = {
		UUID.randomUUID().toString
	}

	def nonce() = {
		val format = new java.text.SimpleDateFormat("yyyyMMddHH")
		Crypto.sign(format.format(new java.util.Date()))
	}
}
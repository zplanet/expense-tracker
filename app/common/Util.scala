package common

import java.security.MessageDigest;

object Util {
	def sha256(msg: String) = {
		val md = MessageDigest.getInstance("SHA-256")
		md.update(msg.getBytes())
		(md.digest() map (x => "%02x" format (x & 0xff))).mkString("")
	}
}
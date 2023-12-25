import java.security.MessageDigest
import java.security.SecureRandom

class UserEncoding {
    fun generateSalt(): ByteArray {
        val random = SecureRandom()
        val salt = ByteArray(16)
        random.nextBytes(salt)
        return salt
    }

    fun hashPassword(password: String, salt: ByteArray): String {
        val md = MessageDigest.getInstance("SHA-256")
        md.update(salt)
        val hashedBytes = md.digest(password.toByteArray(Charsets.UTF_8))
        return bytesToString(hashedBytes)
    }

    private fun bytesToString(bytes: ByteArray): String {
        val sb = StringBuilder()
        for (b in bytes) {
            sb.append(String.format("%02x", b))
        }
        return sb.toString()
    }
}
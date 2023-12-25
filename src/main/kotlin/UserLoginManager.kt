import com.google.gson.Gson
import java.io.File

class UserLoginManager(private val userEncoding: UserEncoding, private val usersFile: File) {
    private val users: MutableList<User>

    init {
        users = loadUsers()
    }

    private fun loadUsers(): MutableList<User> {
        if (usersFile.exists()) {
            val json = usersFile.readText()
            return Gson().fromJson(json, Array<User>::class.java).toMutableList()
        } else {
            return mutableListOf()
        }
    }

    private fun saveUsers() {
        val json = Gson().toJson(users)
        usersFile.writeText(json)
    }

    fun registerUser(username: String, password: String) {
        val salt = userEncoding.generateSalt()
        val hashedPassword = userEncoding.hashPassword(password, salt)

        val newUser = User(username, hashedPassword, salt)

        users.add(newUser)

        saveUsers()
    }

    fun login(username: String, password: String): Boolean {
        val user = users.find { it.username == username }

        if (user == null) {
            println("Такого пользователя не существует.")
            return false
        }

        val savedPassword = user.hashPassword
        val savedSalt = user.salt

        val hashedPassword = userEncoding.hashPassword(password, savedSalt)

        return savedPassword == hashedPassword
    }

    fun mainRegister() {
        println("Введите логин для регистрации: ")
        val login = readln()
        println("Введите пароль для регистрации: ")
        val password = readln()
        registerUser(login, password)
    }

    fun mainLogin() {
        println("Введите логин для входа: ")
        val login = readln()
        println("Введите пароль для входа: ")
        val password = readln()
        login(login, password)
    }
}
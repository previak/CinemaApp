import java.io.File

fun main() {
    val reader = Reader()

    val usersPath = "data/users.json"
    val usersFile = File(usersPath)
    val userEncoding = UserEncoding()
    val userLoginManager = UserLoginManager(userEncoding, usersFile)

    while (true) {
        println("Зарегистрируйтесь (R) или войдите (L) в свой аккаунт (Выберите R/L): ")
        val choice = readln()

        when (choice) {
            "R" -> {
                userLoginManager.mainRegister()
                userLoginManager.mainLogin()
                break
            }

            "L" -> {
                userLoginManager.mainLogin()
                break
            }

            else -> println("Вы ввели неверную опцию. Выберите опцию R/L.")
        }
    }

    val moviesPath = "data/movies.json"
    val schedulePath = "data/schedule.json"

    val moviesFile = File(moviesPath)
    val scheduleFile = File(schedulePath)

    val movieManager = MovieManager(moviesFile)
    val scheduleManager = ScheduleManager(scheduleFile, movieManager)
    val ticketManager = TicketManager(scheduleManager)

    while (true) {
        println("Меню:")
        println("1. Добавить фильм в прокат")
        println("2. Изменить данные о фильме")
        println("3. Изменить расписание")
        println("4. Продать билет на сеанс")
        println("5. Оформить возврат билета на сеанс")
        println("6. Отметить посетителя сеанса")
        println("7. Проверить занятость зала на сеансе")
        println("8. Выход")
        print("Выберите вариант: ")

        val choice = reader.readValidInt()

        when (choice) {
            1 -> movieManager.addMovie()
            2 -> movieManager.changeMovie()
            3 -> scheduleManager.changeSchedule()
            4 -> ticketManager.sellTicket()
            5 -> ticketManager.returnTicket()
            6 -> ticketManager.checkTicket()
            7 -> ticketManager.checkFullnessOfTheHall()
            8 -> {
                println("Вы успешно вышли.")
                return
            }

            else -> println("Вы ввели неверное число. Выберите опцию 1-8.")
        }
        println()
    }

}
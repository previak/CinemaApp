const val MIN_YEAR = 2024
const val MIN_MONTH = 1
const val MAX_MONTH = 12
const val MIN_DAY = 1
const val MAX_DAY = 28
const val MIN_HOUR = 0
const val MAX_HOUR = 23
const val MIN_MINUTE = 0
const val MAX_MINUTE = 59

class DateManager {

    fun createDate() : Date {
        val reader = Reader()

        println("Введите год показа фильма: ")
        var year = reader.readValidInt()
        while (year < MIN_YEAR) {
            println("Кинотеатр начинает работать с 2024 года. Введите другой год: ")
            year = reader.readValidInt()
        }

        println("Введите месяц показа фильма: ")
        var month = reader.readValidInt()
        while (month < MIN_MONTH || month > MAX_MONTH) {
            println("В году только 12 месяцев. Введите другой месяц: ")
            month = reader.readValidInt()
        }

        println("Введите день показа фильма (Кинотеатр работает только первые 28 дней каждого месяца): ")
        var day = reader.readValidInt()
        while (day < MIN_DAY || day > MAX_DAY) {
            println("Кинотеатр работает только первые 28 дней каждого месяца. Введите другой день: ")
            day = reader.readValidInt()
        }

        println("Введите час показа фильма (0-23): ")
        var hour = reader.readValidInt()
        while (hour < MIN_HOUR || hour > MAX_HOUR) {
            println("В дне только 24 часа (0-23). Введите другой час: ")
            hour = reader.readValidInt()
        }

        println("Введите минуту показа фильма (0-59): ")
        var minute = reader.readValidInt()
        while (minute < MIN_MINUTE || minute > MAX_MINUTE) {
            println("В часу только 60 минут (0-59). Введите другую минуту: ")
            minute = reader.readValidInt()
        }

        val date = Date(year, month, day, hour, minute)
        return date
    }
}
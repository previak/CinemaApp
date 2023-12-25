import com.google.gson.Gson
import java.io.File

class ScheduleManager(private val scheduleFile: File, private val movieManager: MovieManager) {
    private var schedule: MutableList<ScheduleComponent>

    init {
        schedule = loadSchedule()
    }

    private fun loadSchedule(): MutableList<ScheduleComponent> {
        if (scheduleFile.exists()) {
            val json = scheduleFile.readText()
            return Gson().fromJson(json, Array<ScheduleComponent>::class.java).toMutableList()
        } else {
            return mutableListOf()
        }
    }

    fun saveSchedule() {
        val json = Gson().toJson(schedule)
        scheduleFile.writeText(json)
    }

    fun changeSchedule() {
        while (true) {
            showSchedule()

            println("Если вы хотите добавить фильм в расписание, введите 'A'")
            println("Если вы хотите удалить фильм из расписания, введите 'D'")

            val choice = readln()

            when (choice) {
                "A" -> {
                    addScheduleComponent()
                    return
                }

                "D" -> {
                    removeScheduleComponent()
                    return
                }

                else -> println("Вы ввели неверную опцию. Выберите (A/D).")
            }
            println()
        }
    }

    private fun addScheduleComponent() {
        val reader = Reader()

        movieManager.showAllMovies()

        val movies = movieManager.movies

        println("Введите id фильма, который вы хотите добавить в расписание: ")
        var id = reader.readValidInt()
        while (movies.find { it.id == id } == null) {
            println("Фильма с таким id не существует. Введите id заново: ")
            id = reader.readValidInt()
        }

        val movie = movieManager.getMovieById(id)

        val dateManager = DateManager()
        val date = dateManager.createDate()

        if (movie == null) {
            println("Фильма с таким id не существует.")
            return
        }

        val tickets: MutableList<Ticket> = mutableListOf()
        val scheduleComponent = ScheduleComponent(1, movie, date, tickets)
        schedule.add(scheduleComponent)
        sortSchedule()

        saveSchedule()

        showSchedule()
    }

    private fun removeScheduleComponent() {
        if (schedule.isEmpty()) {
            println("Расписание пусто. Нечего удалять из расписания.")
            return
        }

        val reader = Reader()

        showSchedule()

        println("Выберите номер сеанса, который вы хотите удалить: ")
        var number = reader.readValidInt()
        while (getScheduleComponentByNumber(number) == null) {
            print("Сеанса с таким номером не существует. Введите другой номер: ")
            number = reader.readValidInt()
        }
        val scheduleComponentToRemove = getScheduleComponentByNumber(number)
        schedule.remove(scheduleComponentToRemove)
        println("Сеанс был успешно удален.")

        sortSchedule()

        saveSchedule()

        showSchedule()
    }

    fun showSchedule() {
        if (schedule.isEmpty()) {
            println("Расписание пусто.")
        } else {
            println("Расписание: ")
            for (scheduleComponent in schedule) {
                println(
                    "${scheduleComponent.number}. ${scheduleComponent.movie.title}. " +
                            "Дата сеанса ${scheduleComponent.startDate.day}." +
                            "${scheduleComponent.startDate.month}.${scheduleComponent.startDate.year} " +
                            "${scheduleComponent.startDate.hour}:${scheduleComponent.startDate.minute}"
                )
            }
        }
    }

    private fun sortSchedule() {
        schedule = schedule.sortedBy { it.startDate }.toMutableList()
        schedule = schedule.mapIndexed { index, component ->
            component.copy(number = index + 1)
        }.toMutableList()

        saveSchedule()
    }

    fun getScheduleComponentByNumber(number: Int): ScheduleComponent? {
        return schedule.find { it.number == number }
    }

    fun getScheduleNumber(): Int {
        val reader = Reader()

        var number = reader.readValidInt()
        while (getScheduleComponentByNumber(number) == null) {
            print("Сеанса с таким номером не существует. Введите другой номер: ")
            number = reader.readValidInt()
        }
        return number
    }
}
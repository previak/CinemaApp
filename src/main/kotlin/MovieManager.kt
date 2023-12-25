import com.google.gson.Gson
import java.io.File

class MovieManager(private val moviesFile: File) {
    val movies: MutableList<Movie>

    init {
        movies = loadMovies()
    }

    private fun loadMovies(): MutableList<Movie> {
        if (moviesFile.exists()) {
            val json = moviesFile.readText()
            return Gson().fromJson(json, Array<Movie>::class.java).toMutableList()
        } else {
            return mutableListOf()
        }
    }

    private fun saveMovies() {
        val json = Gson().toJson(movies)
        moviesFile.writeText(json)
    }

    fun showAllMovies() {
        if (movies.isEmpty()) {
            println("В прокате нет фильмов")
        } else {
            println("Список фильмов в прокате: ")
            for (movie in movies) {
                println("id фильма: ${movie.id}; Название: ${movie.title}; Длительность: ${movie.duration} минут")
            }
        }
    }

    fun addMovie() {
        val reader = Reader()

        println("Введите id добавляемого фильма: ")
        var id = reader.readValidInt()
        while (getMovieById(id) != null) {
            println("Фильм с таким id уже существует. Введите id заново: ")
            id = reader.readValidInt()
        }

        println("Введите название добавляемого фильма: ")
        var title = readln()
        while (movies.find { it.title == title } != null) {
            println("Фильм с таким названием уже существует. Введите название заново: ")
            title = readln()
        }

        println("Введите длительность добавляемого фильма: ")
        val duration = reader.readValidInt()

        movies.add(Movie(id, title, duration))

        saveMovies()
    }

    fun changeMovie() {
        val reader = Reader()

        showAllMovies()

        println("Введите id изменяемого фильма: ")
        var id = reader.readValidInt()
        while (movies.find { it.id == id } == null) {
            println("Фильма с таким id не существует. Введите id заново: ")
            id = reader.readValidInt()
        }

        val movie = getMovieById(id)

        while (true) {
            println("Если вы хотите изменить название фильма, введите 'T'")
            println("Если вы хотите изменить длительность фильма, введите 'D'")

            val choice = readln()

            when (choice) {
                "T" -> {
                    println("Введите новое название фильма: ")
                    val newName = readln()
                    movie?.title = newName
                    saveMovies()
                    println("Изменения были успешно применены.")
                    return
                }
                "D" -> {
                    println("Введите новую длительность фильма: ")
                    val newDuration = reader.readValidInt()
                    movie?.duration = newDuration
                    saveMovies()
                    println("Изменения были успешно применены.")
                    return
                }
                else -> println("Вы ввели неверную опцию. Выберите (T/D).")
            }
            println()
        }
    }

    fun getMovieById(id: Int): Movie? {
        return movies.find { it.id == id }
    }
}
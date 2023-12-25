class Reader {
    fun readValidInt(): Int {
        while (true) {
            try {
                val input = readln()
                return input.toInt()
            } catch (e: NumberFormatException) {
                println("Неверный формат числа. Попробуйте снова:")
            }
        }
    }
}
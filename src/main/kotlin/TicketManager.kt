const val MAX_ROW_AND_COLUMN = 8
const val MAX_SEATS = 64

class TicketManager(private val scheduleManager: ScheduleManager) {

    fun sellTicket() {
        scheduleManager.showSchedule()

        println("Выберите номер сеанса для продажи билета: ")
        val number = scheduleManager.getScheduleNumber()

        val scheduleComponent = scheduleManager.getScheduleComponentByNumber(number)

        if (scheduleComponent == null) {
            println("Сеанса с таким номером не существует.")
            return
        }

        showTickets(number)

        println("Выберите место, которое вы хотите продать: ")

        val (row, column) = getRowAndColumn()

        if (checkTicketWasBought(number, row, column)) {
            println("Билет с таким местом уже был продан.")
            return
        } else {
            val newTicket = Ticket(row, column, false)
            scheduleComponent.tickets.add(newTicket)
            println("Билет был успешно продан.")
        }

        scheduleManager.saveSchedule()
    }

    fun returnTicket() {
        scheduleManager.showSchedule()

        println("Выберите номер сеанса для возврата билета: ")
        val number = scheduleManager.getScheduleNumber()

        val scheduleComponent = scheduleManager.getScheduleComponentByNumber(number)

        if (scheduleComponent == null) {
            println("Сеанса с таким номером не существует.")
            return
        }

        if (scheduleComponent.tickets.size == 0) {
            println("На этот сеанс еще не было продано билетов.")
            return
        }

        showTickets(number)

        println("Выберите место билета, которого вы хотите вернуть: ")

        val (row, column) = getRowAndColumn()

        if (checkTicketWasBought(number, row, column)) {
            scheduleComponent.tickets.remove(scheduleComponent.tickets.find { it.row == row && it.column == column})
            println("Билет был возвращен.")
        } else {
            println("Билет с таким местом еще не был продан.")
            return
        }

        scheduleManager.saveSchedule()
    }

    fun checkTicket() {
        scheduleManager.showSchedule()

        println("Выберите номер сеанса, на который пришел посетитель: ")
        val number = scheduleManager.getScheduleNumber()

        val scheduleComponent = scheduleManager.getScheduleComponentByNumber(number)

        if (scheduleComponent == null) {
            println("Сеанса с таким номером не существует.")
            return
        }

        if (scheduleComponent.tickets.size == 0) {
            println("На этот сеанс еще не было продано билетов.")
            return
        }

        showTickets(number)

        println("Выберите место билета, который вы хотите отметить как использованный: ")

        val (row, column) = getRowAndColumn()

        if (checkTicketWasBought(number, row, column)) {
            scheduleComponent.tickets.find { it.row == row && it.column == column}?.isUsed = true
            println("Билет был отмечен как использованный.")
        } else {
            println("Билет с таким местом еще не был продан.")
            return
        }

        scheduleManager.saveSchedule()
    }

    fun checkFullnessOfTheHall() {
        scheduleManager.showSchedule()

        println("Выберите номер сеанса, чью заполненность вы хотите проверить: ")
        val number = scheduleManager.getScheduleNumber()

        val scheduleComponent = scheduleManager.getScheduleComponentByNumber(number)

        if (scheduleComponent == null) {
            println("Сеанса с таким номером не существует.")
            return
        }

        showTickets(number)
        println("Количество мест занято: ${scheduleComponent.tickets.size}")
        println("Количество свободных мест: ${MAX_SEATS - scheduleComponent.tickets.size}")
    }

    private fun showTickets(number: Int) {
        val scheduleComponent = scheduleManager.getScheduleComponentByNumber(number)

        if (scheduleComponent == null) {
            println("Сеанса с таким номером не существует.")
            return
        }

        if (scheduleComponent.tickets.size == 0) {
            println("Билеты на этот сеанс еще не были проданы.")
        } else {
            println("Проданные билеты на сеанс №${scheduleComponent.number}: ")
            val tickets = scheduleComponent.tickets
            for (ticket in tickets) {

                val status: String = if (ticket.isUsed) {
                    "Посетитель отметился"
                } else {
                    "Посетитель еще не отметился"
                }

                println("Место (${ticket.row};${ticket.column}) куплено. ${status}.")
            }
        }
    }

    private fun checkTicketWasBought(number: Int, row: Int, column: Int): Boolean {
        val scheduleComponent = scheduleManager.getScheduleComponentByNumber(number)

        if (scheduleComponent == null) {
            println("Сеанса с таким номером не существует.")
            return false
        }

        val tickets = scheduleComponent.tickets

        var foundTicket = false

        for (ticket in tickets) {
            if (ticket.row == row && ticket.column == column) {
                foundTicket = true
                break
            }
        }

        return foundTicket
    }

    private fun getRowAndColumn() : Pair<Int, Int> {
        val reader = Reader()

        println("Введите номер ряда (1-8): ")
        var row = reader.readValidInt()
        while (row < 1 || row > MAX_ROW_AND_COLUMN) {
            print("Номер ряда должен быть от 1 до 8. Введите номер ряда: ")
            row = reader.readValidInt()
        }

        println("Введите номер места в выбранном ряду (1-8): ")
        var column = reader.readValidInt()
        while (column < 1 || column > MAX_ROW_AND_COLUMN) {
            print("Номер места должен быть от 1 до 8. Введите номер: ")
            column = reader.readValidInt()
        }

        return Pair(row, column)
    }
}
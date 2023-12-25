data class Date(val year: Int, val month: Int, val day: Int, val hour: Int, val minute: Int) : Comparable<Date> {

    override fun compareTo(other: Date): Int {
        if (year != other.year) {
            return year.compareTo(other.year)
        }
        if (month != other.month) {
            return month.compareTo(other.month)
        }
        if (day != other.day) {
            return day.compareTo(other.day)
        }
        if (hour != other.hour) {
            return hour.compareTo(other.hour)
        }
        return minute.compareTo(other.minute)
    }
}
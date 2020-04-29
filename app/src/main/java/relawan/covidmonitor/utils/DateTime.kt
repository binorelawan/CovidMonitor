package relawan.covidmonitor.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateTime {

    private fun formatDate(date: String): String {
        var result = ""
        val old = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        old.timeZone = TimeZone.getTimeZone("UTC")
        try {
            val oldDate = old.parse(date)
            val newFormat = SimpleDateFormat("HH:mm - dd MMM yyyy", Locale.getDefault())
            newFormat.timeZone = TimeZone.getDefault()

            result = newFormat.format(oldDate!!)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return result
    }


    fun getDate(date: String?): String {
        return formatDate(date.toString())
    }

    fun getDateFormatted(updated: Long) : String{
        val formatter = SimpleDateFormat("HH:mm - dd MMM yyyy", Locale.getDefault());
        return formatter.format(Date(updated))
    }

}
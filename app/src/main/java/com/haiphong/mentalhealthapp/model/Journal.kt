package com.haiphong.mentalhealthapp.model


import com.google.firebase.Timestamp
import java.util.Calendar
import java.util.Date


data class Journal(
    val journalId: String = "",
    val writtenBy: String = "",
    val title: String = "",
    val content: String = "",
    val date: Timestamp = Timestamp(Date())
) {

    fun toHashMap(): HashMap<String, Any> {
        return hashMapOf(
            "content" to content,
            "title" to title,
            "writtenBy" to writtenBy,
            "date" to date
        )
    }

    private fun getDayOfWeek(num: Int): String {
        return when (num) {
            1 -> "Sunday"
            2 -> "Monday"
            3 -> "Tuesday"
            4 -> "Wednesday"
            5 -> "Thursday"
            6 -> "Friday"
            7 -> "Saturday"
            else -> "N/A"
        }
    }

    fun contentOnCard(): String {
        return if (content.length > 20) content.substring(0, 20) + "..." else content
    }

    fun writtenAt(): String {
        val cal = Calendar.getInstance()
        cal.time = date.toDate()
        return " ${cal.get(Calendar.HOUR)}:${cal.get(Calendar.MINUTE)}"
    }

    fun fullDate(): String {
        val cal = Calendar.getInstance()
        cal.time = date.toDate()
        return "${cal.get(Calendar.HOUR)}:${cal.get(Calendar.MINUTE)} ${
            getDayOfWeek(
                cal.get(
                    Calendar.DAY_OF_WEEK
                )
            )
        }, ${cal.get(Calendar.DAY_OF_MONTH)}/${cal.get(Calendar.MONTH)}/${cal.get(Calendar.YEAR)}"
    }

    fun fullDateWithoutHour(): String {
        val cal = Calendar.getInstance()
        cal.time = date.toDate()
        return "${
            getDayOfWeek(
                cal.get(
                    Calendar.DAY_OF_WEEK
                )
            )
        }, ${cal.get(Calendar.DAY_OF_MONTH)}/${cal.get(Calendar.MONTH)}/${cal.get(Calendar.YEAR)}"
    }
}
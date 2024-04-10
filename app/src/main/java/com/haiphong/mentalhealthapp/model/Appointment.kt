package com.haiphong.mentalhealthapp.model

import com.google.firebase.Timestamp
import java.util.Date

data class Appointment(
    val appointmentId: String = "",
    val customerId: String = "",
    val specialistId: String = "",
    val date: String = "",
    val dayOfWeek: String = "",
    val price: String = "",
    val session: String = "",
    val epochDay: Long = 0,
    val timestamp: Timestamp = Timestamp(Date())
) {
    fun toHashMap(): HashMap<String, Any> {
        return hashMapOf(
            "customerId" to customerId,
            "specialistId" to specialistId,
            "date" to date,
            "dayOfWeek" to dayOfWeek,
            "price" to price,
            "session" to session,
            "epochDay" to epochDay,
            "timestamp" to timestamp
        )
    }
}
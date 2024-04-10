package com.haiphong.mentalhealthapp.model.repositories

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.haiphong.mentalhealthapp.model.Specialist
import kotlinx.coroutines.tasks.await

interface SpecialistRepository {
    suspend fun getSpecialist(id: String): Specialist?
    suspend fun getScheduledSlots(
        specialistId: String,
        customerId: String
    ): List<Pair<Long, String>>

    suspend fun getBothScheduledSlots(
        specialistId: String,
        customerId: String
    ): List<Pair<Long, String>>
}

class SpecialistRepositoryImpl : SpecialistRepository {

    private fun convertTimeSlots(data: List<String>): Array<Array<Int>> {
        val result = Array(7) { Array(12) { 0 } }
        data.forEach {
            val session = it.substring(0, 2).toInt()
            val day = it[2].digitToInt()
            result[day][session] = 1
        }

        return result
    }

    override suspend fun getSpecialist(id: String): Specialist? {
        return try {
            val doc = Firebase.firestore.collection("specialists")
                .document(id).get().await()
            val data = doc.data
            Specialist(
                specialistId = doc.id,
                name = data!!["name"].toString(),
                gender = data["gender"].toString(),
                date = data["date"].toString(),
                month = data["month"].toString(),
                year = data["year"].toString(),
                bio = data["bio"].toString(),
                avatarPath = data["avatarPath"].toString(),
                workplace = data["workplace"].toString(),
                pricePerSession = data["pricePerSession"].toString(),
                credentials = data["credentials"] as List<String>,
                availableTimeSlots = convertTimeSlots(data["availableTimeSlots"] as List<String>),
            )
        } catch (e: FirebaseFirestoreException) {
            Log.d("Get Specialist", "Error", e)
            null
        }
    }

    override suspend fun getScheduledSlots(
        specialistId: String,
        customerId: String
    ): List<Pair<Long, String>> {
        val result = mutableSetOf<Pair<Long, String>>()

        val specialistAppointments =
            Firebase.firestore.collection("appointments").whereEqualTo("specialistId", specialistId)
                .get().await()
        val customerAppointments =
            Firebase.firestore.collection("appointments").whereEqualTo("customerId", customerId)
                .get().await()
        for (appointment in specialistAppointments.documents) {
            val data = appointment.data
            val epochDay = data!!["epochDay"] as Long
            val session = data["session"].toString()
            result.add(Pair(epochDay, session))
        }
        for (appointment in customerAppointments.documents) {
            val data = appointment.data
            val epochDay = data!!["epochDay"] as Long
            val session = data["session"].toString()
            result.add(Pair(epochDay, session))
        }
        Log.d("Get Scheduled Slots", "repository: ${result.joinToString()}")
        return result.toList()
    }

    // used in constraint to 1 week
    override suspend fun getBothScheduledSlots(
        specialistId: String,
        customerId: String
    ): List<Pair<Long, String>> {
        val result = mutableListOf<Pair<Long, String>>()
        val appointments =
            Firebase.firestore.collection("appointments").whereEqualTo("customerId", customerId)
                .whereEqualTo("specialistId", specialistId).get().await()
        for (appointment in appointments.documents) {
            val data = appointment.data
            val epochDay = data!!["epochDay"] as Long
            val session = data["session"].toString()
            result.add(Pair(epochDay, session))
        }

        return result.toList()
    }


}
package com.haiphong.mentalhealthapp.viewmodel.specialistHome

import androidx.lifecycle.ViewModel
import com.google.firebase.Timestamp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.haiphong.mentalhealthapp.UserAuthentication
import com.haiphong.mentalhealthapp.model.Appointment
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date

class SpecialistHomeViewModel : ViewModel() {
    private val _appointmentsList = MutableStateFlow(listOf<Appointment>())
    val appointmentList = _appointmentsList.asStateFlow()

    init {
        getData()
    }

    private fun getData() {
        val db = Firebase.firestore
        db.collection("appointments").whereEqualTo("specialistId", UserAuthentication.getUid())
            .get()
            .addOnSuccessListener { documents ->
                val docList = documents.documents
                val appointmentList = mutableListOf<Appointment>()
                docList.forEach {
                    val timestamp: Timestamp = it.data!!["timestamp"] as Timestamp
                    if (timestamp > Timestamp(
                            Date.from(
                                LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault())
                                    .atZone(
                                        ZoneId.systemDefault()
                                    ).toInstant()
                            )
                        )
                    ) {
                        appointmentList.add(
                            Appointment(
                                appointmentId = it.id,
                                date = it.data!!["date"].toString(),
                                session = it.data!!["session"].toString(),
                                dayOfWeek = it.data!!["dayOfWeek"].toString()
                            )
                        )
                    }

                }
                _appointmentsList.update {
                    appointmentList
                }
            }
    }
}
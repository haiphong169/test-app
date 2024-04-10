package com.haiphong.mentalhealthapp.viewmodel.bookAppointment

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Timestamp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.haiphong.mentalhealthapp.UserAuthentication
import com.haiphong.mentalhealthapp.model.Appointment
import com.haiphong.mentalhealthapp.model.Specialist
import com.haiphong.mentalhealthapp.model.repositories.SpecialistRepository
import com.haiphong.mentalhealthapp.model.repositories.SpecialistRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date

private const val limit = 14

class SpecialistInfoViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {

    private val _specialist = MutableStateFlow(Specialist())
    val specialist = _specialist.asStateFlow()

    val dateList = mutableListOf<LocalDate>()

    private val _table = MutableStateFlow(Array(limit) { Array(12) { 0 } })
    val table = _table.asStateFlow()

    private lateinit var scheduledSlots: List<Pair<Long, String>>

    private lateinit var bothScheduledSlots: List<Pair<Long, String>>

    private val specialistId: String = checkNotNull(savedStateHandle["specialistId"])

    private val specialistRepository: SpecialistRepository = SpecialistRepositoryImpl()

    init {
        for (i in 1..limit) {
            dateList.add(LocalDate.now().plusDays(i.toLong()))
        }
        viewModelScope.launch {
            getData()
            prepareData()
        }

    }

    private fun prepareData() {
        val result = Array(limit) { Array(12) { 0 } }
        // get the normal schedule
        for (i in 0..13) {
            for (j in 0..11) {
                result[i][j] =
                    specialist.value.availableTimeSlots[(i + LocalDate.now().dayOfWeek.value) % 7][j]
            }
        }
        //get rid of already scheduled slots from customer and specialist
        for (pair in convert(scheduledSlots)) {
            result[pair.first][pair.second] = 2
        }
        //zero the week that an appointment between the customer and specialist already existed
        for (pair in convert(bothScheduledSlots)) {
            for (i in (pair.first - (LocalDate.now().dayOfWeek.value + pair.first + 1 - 1))..(pair.first + (7 - LocalDate.now().dayOfWeek.value - pair.first - 1))) {
                if (i < 0) {
                    continue
                } else if (i == limit) {
                    break
                } else {
                    val list = result[i].toMutableList()
                    list.replaceAll { 3 }
                    if (i == pair.first) list[pair.second] = 4
                    result[i] = list.toTypedArray()
                }
            }
        }
        _table.update {
            result
        }
    }

    private fun convert(list: List<Pair<Long, String>>): List<Pair<Int, Int>> {
        val result = mutableListOf<Pair<Int, Int>>()
        for (appointment in list) {
            val date = LocalDate.ofEpochDay(appointment.first)
            val session = appointment.second.substring(0, 2).toInt()

            val columnNum: Int = if (date.monthValue == LocalDate.now().monthValue) {
                date.dayOfMonth - LocalDate.now().dayOfMonth - 1
            } else {
                date.dayOfMonth + (LocalDate.now()
                    .lengthOfMonth() - LocalDate.now().dayOfMonth) - 1
            }
            val rowNum = session - 7
            result.add(Pair(columnNum, rowNum))
        }

        return result
    }

    private suspend fun getData() {
        _specialist.update {
            specialistRepository.getSpecialist(specialistId) ?: Specialist()
        }
        scheduledSlots =
            specialistRepository.getScheduledSlots(specialistId, UserAuthentication.getUid())
        bothScheduledSlots =
            specialistRepository.getBothScheduledSlots(specialistId, UserAuthentication.getUid())

    }

    fun createAppointment(onComplete: () -> Unit, date: LocalDate, session: String) {
        val hour = session.substring(0, 2).toInt()
        val zoneId = ZoneId.of("Asia/Ho_Chi_Minh")
        val appointmentTime = Date.from(date.atTime(hour, 5).atZone(zoneId).toInstant())
        val timestamp = Timestamp(appointmentTime)
        val newAppointment = Appointment(
            customerId = UserAuthentication.getUid(),
            specialistId = specialistId,
            date = "${date.month} ${date.dayOfMonth}",
            dayOfWeek = date.dayOfWeek.toString(),
            price = specialist.value.pricePerSession,
            session = session,
            epochDay = date.toEpochDay(),
            timestamp = timestamp
        )
        val db = Firebase.firestore
        db.collection("appointments").add(newAppointment.toHashMap()).addOnSuccessListener {
            onComplete()
        }
    }
}
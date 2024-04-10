package com.haiphong.mentalhealthapp.viewmodel.bookAppointment

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.haiphong.mentalhealthapp.model.Appointment
import com.haiphong.mentalhealthapp.model.Specialist
import com.haiphong.mentalhealthapp.model.User
import com.haiphong.mentalhealthapp.model.repositories.SpecialistRepository
import com.haiphong.mentalhealthapp.model.repositories.SpecialistRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.tasks.await

data class AppointmentState(
    val appointment: Appointment = Appointment(),
    val specialist: Specialist = Specialist(),
    val customer: User = User(),
    val openDialog: Boolean = false
)

class AppointmentDetailsViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {
    private val appointmentId: String = checkNotNull(savedStateHandle["appointmentId"])
    private var _appointmentState = MutableStateFlow(AppointmentState())
    val appointmentState = _appointmentState.asStateFlow()

    private val specialistRepository: SpecialistRepository = SpecialistRepositoryImpl()

    init {
        viewModelScope.launch {
            getData()
        }
    }

    fun changeOpenDialog(newState: Boolean) {
        _appointmentState.update {
            it.copy(
                openDialog = newState
            )
        }
    }


    private suspend fun getData() {
        _appointmentState.update {
            it.copy(
                appointment = getAppointment() ?: Appointment()
            )
        }
        _appointmentState.update {
            it.copy(
                specialist = getSpecialist() ?: Specialist(),
                customer = getCustomer() ?: User()
            )
        }
    }

    private suspend fun getSpecialist(): Specialist? {
        return specialistRepository.getSpecialist(appointmentState.value.appointment.specialistId)
    }

    private suspend fun getAppointment(): Appointment? {
        return try {
            Log.d("Get Appointment", "Appointment ID: $appointmentId")
            val doc =
                Firebase.firestore.collection("appointments").document(appointmentId).get().await()
            val data = doc.data
            Appointment(
                specialistId = data!!["specialistId"].toString(),
                customerId = data["customerId"].toString(),
                date = data["date"].toString(),
                dayOfWeek = data["dayOfWeek"].toString(),
                session = data["session"].toString(),
                price = data["price"].toString(),
            )
        } catch (e: FirebaseFirestoreException) {
            Log.d("Get Appointment", "Error", e)
            null
        }
    }

//    private fun convertTimeSlots(data: List<String>): Array<Array<Int>> {
//        val result = Array(7) { Array(12) { 0 } }
//        data.forEach {
//            val session = it.substring(0, 2).toInt()
//            val day = it[2].digitToInt()
//            result[day][session] = 1
//        }
//
//        return result
//    }

//    private suspend fun getSpecialist(): Specialist? {
//        return try {
//            val doc = Firebase.firestore.collection("specialists")
//                .document(appointmentState.value.appointment.specialistId).get().await()
//            val data = doc.data
//            Specialist(
//                specialistId = doc.id,
//                name = data!!["name"].toString(),
//                gender = data["gender"].toString(),
//                date = data["date"].toString(),
//                month = data["month"].toString(),
//                year = data["year"].toString(),
//                bio = data["bio"].toString(),
//                avatarPath = data["avatarPath"].toString(),
//                workplace = data["workplace"].toString(),
//                pricePerSession = data["pricePerSession"].toString(),
//                credentials = data["credentials"] as List<String>,
//                availableTimeSlots = convertTimeSlots(data["availableTimeSlots"] as List<String>)
//            )
//        } catch (e: FirebaseFirestoreException) {
//            Log.d("Get Specialist", "Error", e)
//            null
//        }
//    }

    private suspend fun getCustomer(): User? {
        return try {
            val doc = Firebase.firestore.collection("customers")
                .document(appointmentState.value.appointment.customerId).get().await()
            val data = doc.data
            User(
                customerId = doc.id,
                name = data?.get("name").toString(),
                gender = data?.get("gender").toString(),
                date = data?.get("date").toString(),
                month = data?.get("month").toString(),
                year = data?.get("year").toString(),
                bio = data?.get("bio").toString(),
                avatarPath = data?.get("avatarPath").toString(),
            )
        } catch (e: FirebaseFirestoreException) {
            Log.d("Get User", "Error", e)
            null
        }
    }

    fun cancelAppointment(onDelete: () -> Unit) {
        Firebase.firestore.collection("appointments").document(appointmentId).delete()
            .addOnSuccessListener {
                onDelete()
            }
    }
}


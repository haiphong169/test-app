package com.haiphong.mentalhealthapp.viewmodel.request

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.haiphong.mentalhealthapp.model.Specialist
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class EmailAndPassword(val email: String = "", val password: String = "")

class RequestViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {
    private val _specialist = MutableStateFlow(Specialist())
    val specialist = _specialist.asStateFlow()
    private val _emailAndPassword = MutableStateFlow(EmailAndPassword())

    private val requestId: String = checkNotNull(savedStateHandle["requestId"])

    init {
        getData()
    }

    private fun convertTimeSlots(data: List<String>): Array<Array<Int>> {
        val result = Array(7) { Array(12) { 0 } }
        data.forEach {
            val row = it.substring(0, 2).toInt()
            val column = it[2].digitToInt()
            result[column][row] = 1
        }

        return result
    }

    private fun getData() {
        val db = Firebase.firestore
        db.collection("specialistRequests").document(requestId).get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val data = document.data
                    val newSpecialist = Specialist(
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
                        availableTimeSlots = convertTimeSlots(data["availableTimeSlots"] as List<String>)
                    )
                    val eap = EmailAndPassword(
                        email = data["email"].toString(),
                        password = data["password"].toString()
                    )
                    _specialist.update {
                        newSpecialist
                    }
                    _emailAndPassword.update {
                        eap
                    }
                }
            }
    }

    fun acceptRequest(onComplete: () -> Unit) {
        val db = Firebase.firestore
        val auth = Firebase.auth
        auth.createUserWithEmailAndPassword(
            _emailAndPassword.value.email,
            _emailAndPassword.value.password
        ).addOnSuccessListener {
            if (it.user != null) {
                val uid = it.user!!.uid
                db.collection("specialists").document(uid).set(
                    _specialist.value.toHashMap()
                ).addOnSuccessListener {
                    db.collection("specialistRequests").document(requestId).delete()
                    auth.signOut()
                    onComplete()
                }
            }

        }
    }

    fun declineRequest(onComplete: () -> Unit) {
        val db = Firebase.firestore
        db.collection("specialistRequests").document(requestId).delete().addOnSuccessListener {
            onComplete()
        }
    }
}
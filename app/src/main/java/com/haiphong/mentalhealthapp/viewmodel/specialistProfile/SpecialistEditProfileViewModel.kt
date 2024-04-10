package com.haiphong.mentalhealthapp.viewmodel.specialistProfile

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.haiphong.mentalhealthapp.UserAuthentication
import com.haiphong.mentalhealthapp.model.Specialist
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SpecialistEditProfileViewModel : ViewModel() {
    private val _specialist = MutableStateFlow(Specialist())
    val specialist = _specialist.asStateFlow()

    init {
        getData()
    }

    private fun convertTimeSlots(data: List<String>): Array<Array<Int>> {
        val result = Array(12) { Array(7) { 0 } }
        data.forEach {
            val row = it.substring(0, 2).toInt()
            val column = it[2].digitToInt()
            result[row][column] = 1
        }

        return result
    }

    fun onNameChange(nString: String) {
        _specialist.update {
            it.copy(
                name = nString
            )
        }
    }

    fun onGenderChange(nString: String) {
        _specialist.update {
            it.copy(
                gender = nString
            )
        }
    }

    fun onDateChange(nString: String) {
        _specialist.update {
            it.copy(
                date = nString
            )
        }
    }

    fun onMonthChange(nString: String) {
        _specialist.update {
            it.copy(
                month = nString
            )
        }
    }

    fun onYearChange(nString: String) {
        _specialist.update {
            it.copy(
                year = nString
            )
        }
    }

    fun onBioChange(nString: String) {
        _specialist.update {
            it.copy(
                bio = nString
            )
        }
    }

    fun onAvatarChange(nString: String) {
        _specialist.update {
            it.copy(
                avatarPath = nString
            )
        }
    }

    fun onWorkplaceChange(nString: String) {
        _specialist.update {
            it.copy(
                workplace = nString
            )
        }
    }

    fun onPriceChange(nNum: String) {
        _specialist.update {
            it.copy(
                pricePerSession = nNum
            )
        }
    }

    fun addCredentials(nString: String) {
        _specialist.update { currentState ->
            currentState.copy(
                credentials = listOf(*currentState.credentials.toTypedArray(), nString)
            )
        }
    }

    private fun getSubarray(up: Int, down: Int): Array<Array<Int>> {
        var result: Array<Array<Int>> = arrayOf()
        for (i in up..down) {
            result += specialist.value.availableTimeSlots[i]
        }
        return result
    }

    private fun getArray(row: Int, column: Int, delete: Boolean): Array<Int> {
        var result: Array<Int> = arrayOf()
        for (i in 0..6) {
            result += specialist.value.availableTimeSlots[row][i]
        }
        result[column] = if (!delete) 1 else 0
        return result
    }


    fun addAvailableTimeSlot(row: Int, column: Int) {
        _specialist.update {
            it.copy(
                availableTimeSlots = getSubarray(0, row - 1) + getArray(
                    row,
                    column,
                    false
                ) + getSubarray(row + 1, 11)
            )
        }
    }

    fun removeTimeSlot(row: Int, column: Int) {
        _specialist.update {
            it.copy(

                availableTimeSlots = getSubarray(0, row - 1) + getArray(
                    row,
                    column,
                    true
                ) + getSubarray(row + 1, 11)
            )
        }
    }

    private fun getData() {
        val db = Firebase.firestore
        db.collection("specialists").document(UserAuthentication.getUid()).get()
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
                    _specialist.update {
                        newSpecialist
                    }

                }
            }
    }

    fun submitForm(onComplete: () -> Unit) {
        val db = Firebase.firestore
        db.collection("specialistRequests")
            .add(
                _specialist.value.toHashMap() + hashMapOf(
                    "uid" to UserAuthentication.getUid(),
                    "type" to "Update"
                )
            ).addOnSuccessListener {
                onComplete()
            }
    }
}
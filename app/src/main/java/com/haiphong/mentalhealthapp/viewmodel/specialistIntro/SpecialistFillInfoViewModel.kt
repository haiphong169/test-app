package com.haiphong.mentalhealthapp.viewmodel.specialistIntro

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.haiphong.mentalhealthapp.UserAuthentication
import com.haiphong.mentalhealthapp.model.Specialist
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


class SpecialistFillInfoViewModel : ViewModel() {
    private val _fillInfoState = MutableStateFlow(Specialist())
    val fillInfoState = _fillInfoState.asStateFlow()

    fun onNameChange(nString: String) {
        _fillInfoState.update {
            it.copy(
                name = nString
            )
        }
    }

    fun onGenderChange(nString: String) {
        _fillInfoState.update {
            it.copy(
                gender = nString
            )
        }
    }

    fun onDateChange(nString: String) {
        _fillInfoState.update {
            it.copy(
                date = nString
            )
        }
    }

    fun onMonthChange(nString: String) {
        _fillInfoState.update {
            it.copy(
                month = nString
            )
        }
    }

    fun onYearChange(nString: String) {
        _fillInfoState.update {
            it.copy(
                year = nString
            )
        }
    }

    fun onBioChange(nString: String) {
        _fillInfoState.update {
            it.copy(
                bio = nString
            )
        }
    }

    fun onAvatarChange(nString: String) {
        _fillInfoState.update {
            it.copy(
                avatarPath = nString
            )
        }
    }

    fun onWorkplaceChange(nString: String) {
        _fillInfoState.update {
            it.copy(
                workplace = nString
            )
        }
    }

    fun onPriceChange(nNum: String) {
        _fillInfoState.update {
            it.copy(
                pricePerSession = nNum
            )
        }
    }

    fun addCredentials(nString: String) {
        _fillInfoState.update { currentState ->
            currentState.copy(
                credentials = listOf(*currentState.credentials.toTypedArray(), nString)
            )
        }
    }

    private fun getSubarray(up: Int, down: Int): Array<Array<Int>> {
        var result: Array<Array<Int>> = arrayOf()
        for (i in up..down) {
            result += fillInfoState.value.availableTimeSlots[i]
        }
        return result
    }

    private fun getArray(row: Int, column: Int, delete: Boolean): Array<Int> {
        var result: Array<Int> = arrayOf()
        for (i in 0..11) {
            result += fillInfoState.value.availableTimeSlots[row][i]
        }
        result[column] = if (!delete) 1 else 0
        return result
    }


    fun addAvailableTimeSlot(row: Int, column: Int) {
        _fillInfoState.update {
            it.copy(
                availableTimeSlots = getSubarray(0, row - 1) + getArray(
                    row,
                    column,
                    false
                ) + getSubarray(row + 1, 6)
            )
        }
    }

    fun removeTimeSlot(row: Int, column: Int) {
        _fillInfoState.update {
            it.copy(

                availableTimeSlots = getSubarray(0, row - 1) + getArray(
                    row,
                    column,
                    true
                ) + getSubarray(row + 1, 6)
            )
        }
    }

    fun sendRequest(onComplete: () -> Unit) {
        val db = Firebase.firestore
        db.collection("specialistRequests").add(
            fillInfoState.value.toHashMap() + hashMapOf<String, Any>(
                "email" to UserAuthentication.specialistEmail,
                "password" to UserAuthentication.specialistPassword
            )
        )
            .addOnSuccessListener {
                Log.d("Create Specialist Request", "success")
                onComplete()
            }.addOnFailureListener { exception ->
                Log.d(
                    "Create Specialist Request",
                    "failed with",
                    exception
                )
            }
    }
}
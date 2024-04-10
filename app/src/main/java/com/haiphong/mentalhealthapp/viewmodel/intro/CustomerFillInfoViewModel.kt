package com.haiphong.mentalhealthapp.view.util.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.haiphong.mentalhealthapp.UserAuthentication
import com.haiphong.mentalhealthapp.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class FillInfoState(
    val name: String = "",
    val gender: String = "",
    val date: String = "",
    val month: String = "",
    val year: String = "",
    val bio: String = "",
    val avatarString: String = "",
    val invalidated: Boolean = false,
    val errorMessage: String = ""
)

class CustomerFillInfoViewModel : ViewModel() {
    private val _fillInfoState = MutableStateFlow(FillInfoState())
    val fillInfoState = _fillInfoState.asStateFlow()

    fun onNameChange(newString: String) {
        _fillInfoState.update { currentState ->
            currentState.copy(
                name = newString
            )
        }
    }
    fun onGenderChange(newString: String) {
        _fillInfoState.update { currentState ->
            currentState.copy(
                gender = newString
            )
        }
    }
    fun onDateChange(newString: String) {
        _fillInfoState.update { currentState ->
            currentState.copy(
                date = newString
            )
        }
    }
    fun onMonthChange(newString: String) {
        _fillInfoState.update { currentState ->
            currentState.copy(
                month = newString
            )
        }
    }
    fun onYearChange(newString: String) {
        _fillInfoState.update { currentState ->
            currentState.copy(
                year = newString
            )
        }
    }
    fun onBioChange(newString: String) {
        _fillInfoState.update { currentState ->
            currentState.copy(
                bio = newString
            )
        }
    }
    fun onAvatarChange(newString: String) {
        _fillInfoState.update { currentState ->
            currentState.copy(
                avatarString = newString
            )
        }
    }

    fun clearForm() {
        _fillInfoState.update { currentState ->
            currentState.copy(
                name = "",
                gender = "",
                date = "",
                month = "",
                year = "",
                bio = "",
                avatarString = "",
                invalidated = true,
                errorMessage = ""
            )
        }
    }

    fun setErrorMessage(errorMessage: String) {
        _fillInfoState.update { currentState ->
            currentState.copy(
                invalidated = true,
                errorMessage = errorMessage
            )
        }
    }

    fun updateUser(updatedInfo: User) {
        val db = Firebase.firestore
        val update = updatedInfo.toHashMap()
        db.collection("customers").document(UserAuthentication.getUid()).set(update)
            .addOnSuccessListener {
                Log.d("FillInfoUser", "DocumentSnapshot updated")
            }.addOnFailureListener { e -> Log.w("FillInfoUser", "Error adding document", e) }
    }
}
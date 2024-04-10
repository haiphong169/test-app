package com.haiphong.mentalhealthapp.viewmodel.intro

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.haiphong.mentalhealthapp.UserAuthentication
import com.haiphong.mentalhealthapp.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class SignUpState(
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val errorMessage: String = "",
    val invalidated: Boolean = false
)

class CustomerSignUpViewModel : ViewModel() {
    private val _signUpState = MutableStateFlow(SignUpState())
    val signUpState: StateFlow<SignUpState> = _signUpState.asStateFlow()

    fun onEmailChange(newEmail: String) {
        _signUpState.update { currentState ->
            currentState.copy(
                email = newEmail
            )
        }
    }

    fun onPasswordChange(newPassword: String) {
        _signUpState.update { currentState ->
            currentState.copy(
                password = newPassword
            )
        }
    }

    fun onConfirmPasswordChange(newPassword: String) {
        _signUpState.update { currentState ->
            currentState.copy(
                confirmPassword = newPassword
            )
        }
    }

    fun setErrorMessage(errorMessage: String) {
        _signUpState.update { currentState ->
            currentState.copy(
                errorMessage = errorMessage,
                invalidated = true
            )
        }
    }

    fun clearForm() {
        _signUpState.update { currentState ->
            currentState.copy(
                email = "",
                password = "",
                errorMessage = "",
                invalidated = false
            )
        }
    }

    fun createNewUser() {
        val db = Firebase.firestore
        val newUser = User().toHashMap()

        db.collection("customers").document(UserAuthentication.getUid()).set(newUser)
            .addOnSuccessListener {
                Log.d("CreateUser", "DocumentSnapshot created")
            }.addOnFailureListener { e -> Log.w("CreateUser", "Error adding document", e) }
    }
}
package com.haiphong.mentalhealthapp.viewmodel.intro

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class SignInState(
    val email: String = "",
    val password: String = "",
    val errorMessage: String = "",
    val invalidated: Boolean = false
)

class CustomerSignInViewModel : ViewModel() {
    private val _signInState = MutableStateFlow(SignInState())
    val signInState: StateFlow<SignInState> = _signInState.asStateFlow()

    fun onEmailChange(newEmail: String) {
        _signInState.update { currentState ->
            currentState.copy(
                email = newEmail
            )
        }
    }

    fun onPasswordChange(newPassword: String) {
        _signInState.update { currentState ->
            currentState.copy(
                password = newPassword
            )
        }
    }

    fun setErrorMessage(errorMessage: String) {
        _signInState.update { currentState ->
            currentState.copy(
                errorMessage = errorMessage,
                invalidated = true
            )
        }
    }

    fun clearForm() {
        _signInState.update { currentState ->
            currentState.copy(
                email = "",
                password = "",
                errorMessage = "",
                invalidated = false
            )
        }
    }
}
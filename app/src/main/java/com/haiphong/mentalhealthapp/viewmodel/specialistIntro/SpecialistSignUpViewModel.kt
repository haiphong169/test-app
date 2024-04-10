package com.haiphong.mentalhealthapp.viewmodel.specialistIntro

import androidx.lifecycle.ViewModel
import com.haiphong.mentalhealthapp.UserAuthentication
import com.haiphong.mentalhealthapp.viewmodel.intro.SignUpState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SpecialistSignUpViewModel: ViewModel() {
    private val _signUpState = MutableStateFlow(SignUpState())
    val signUpState = _signUpState.asStateFlow()

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

    fun onSubmit() {
        UserAuthentication.specialistEmail = signUpState.value.email
        UserAuthentication.specialistPassword = signUpState.value.password
    }
}
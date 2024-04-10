package com.haiphong.mentalhealthapp.view.screens.specialist.intro

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.haiphong.mentalhealthapp.view.composables.StandardTextField
import com.haiphong.mentalhealthapp.viewmodel.specialistIntro.SpecialistSignUpViewModel

@Composable
fun SpecialistSignUpScreen(
    viewModel: SpecialistSignUpViewModel = viewModel(),
    toFillInfo: () -> Unit
) {
    val signUpState by viewModel.signUpState.collectAsState()

    fun signUp() {
        if (signUpState.email == "" || signUpState.password == "" || signUpState.confirmPassword == "") {
            viewModel.setErrorMessage("Please fill every fields.")
            return
        } else if (signUpState.password != signUpState.confirmPassword) {
            viewModel.setErrorMessage("Confirm password doesn't match.")
            return
        }
        viewModel.onSubmit()
        toFillInfo()
        viewModel.clearForm()
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Specialist Sign Up Screen")
        Spacer(modifier = Modifier.height(10.dp))
        StandardTextField(
            value = signUpState.email,
            label = "Email",
            onValueChange = viewModel::onEmailChange,
            isInvalidated = signUpState.invalidated
        )
        StandardTextField(
            value = signUpState.password,
            label = "Password",
            isPassword = true,
            onValueChange = viewModel::onPasswordChange, isInvalidated = signUpState.invalidated
        )
        StandardTextField(
            value = signUpState.confirmPassword,
            label = "Confirm Password",
            placeholder = "Confirm your password",
            isPassword = true,
            onValueChange = viewModel::onConfirmPasswordChange,
            isInvalidated = signUpState.invalidated
        )
        if (signUpState.invalidated) Text(
            text = signUpState.errorMessage,
            color = Color.Red,
            fontSize = 16.sp,
            modifier = Modifier.padding(all = 20.dp)
        )
        Button(onClick = {
            signUp()
        }) {
            Text(text = "Continue")
        }
    }
}
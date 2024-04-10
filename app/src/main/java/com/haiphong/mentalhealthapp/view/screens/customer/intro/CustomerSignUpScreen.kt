package com.haiphong.mentalhealthapp.view.screens.customer.intro

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.haiphong.mentalhealthapp.UserAuthentication
import com.haiphong.mentalhealthapp.view.composables.StandardTextField
import com.haiphong.mentalhealthapp.viewmodel.intro.SignUpState


@Composable
fun CustomerSignUpScreen(
    modifier: Modifier = Modifier,
    onSignUp: () -> Unit,
    signUpState: SignUpState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    toSpecialistForm: () -> Unit,
    clearForm: () -> Unit,
    setErrorMessage: (String) -> Unit,
    createNewUser: () -> Unit
) {


    fun userSignUp() {
        if (signUpState.email == "" || signUpState.password == "") {
            setErrorMessage("Please enter your email and password.")
            return
        } else if(signUpState.password != signUpState.confirmPassword) {
            setErrorMessage("Your passwords didn't match.")
            return
        }
        UserAuthentication.signUp(
            email = signUpState.email,
            password = signUpState.password,
            onSignUp = {
                createNewUser()
                onSignUp()
                clearForm()
            },
            onError = setErrorMessage
        )

    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Customer Sign Up Screen")
        Spacer(modifier = Modifier.height(10.dp))
        StandardTextField(
            value = signUpState.email,
            label = "Email",
            onValueChange = onEmailChange,
            isInvalidated = signUpState.invalidated
        )
        StandardTextField(
            value = signUpState.password,
            label = "Password",
            isPassword = true,
            onValueChange = onPasswordChange, isInvalidated = signUpState.invalidated
        )
        StandardTextField(
            value = signUpState.confirmPassword,
            label = "Confirm Password",
            placeholder = "Confirm your password",
            isPassword = true,
            onValueChange = onConfirmPasswordChange,
            isInvalidated = signUpState.invalidated
        )
        if (signUpState.invalidated) Text(
            text = signUpState.errorMessage,
            color = Color.Red,
            fontSize = 16.sp,
            modifier = Modifier.padding(all = 20.dp)
        )
        TextButton(onClick = toSpecialistForm) {
            Text(text = "Are you a specialist? Go here.")
        }
        Button(onClick = {
            userSignUp()
        }) {
            Text(text = "Continue")
        }
    }
}
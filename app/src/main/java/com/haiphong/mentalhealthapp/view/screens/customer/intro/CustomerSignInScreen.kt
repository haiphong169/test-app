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
import com.haiphong.mentalhealthapp.viewmodel.intro.SignInState

@Composable
fun CustomerSignInScreen(
    modifier: Modifier = Modifier,
    signInState: SignInState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    toSignUp: () -> Unit,
    onSignIn: (String) -> Unit,
    clearForm: () -> Unit,
    adminSignIn: () -> Unit,
    setErrorMessage: (String) -> Unit
) {

    fun determineFlow(userType: String) {
        onSignIn(userType)
        clearForm()
    }

    fun userSignIn() {
        if (signInState.email == "" || signInState.password == "") {
            setErrorMessage("Please enter your email and password.")
            return
        }
        if (signInState.email == "admin" && signInState.password == "password") {
            adminSignIn()
            clearForm()
            return
        } else {
            UserAuthentication.signIn(
                email = signInState.email,
                password = signInState.password,
                onSignIn = ::determineFlow,
                onError = setErrorMessage
            )
        }


    }




    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Customer Sign In Screen")
        StandardTextField(
            value = signInState.email,
            label = "Email",
            onValueChange = onEmailChange,
            isInvalidated = signInState.invalidated
        )
        Spacer(modifier = Modifier.height(10.dp))
        StandardTextField(
            value = signInState.password,
            label = "Password",
            isPassword = true,
            onValueChange = onPasswordChange,
            isInvalidated = signInState.invalidated
        )
        Spacer(modifier = Modifier.height(20.dp))
        if (signInState.invalidated) Text(
            text = signInState.errorMessage,
            color = Color.Red,
            fontSize = 16.sp,
            modifier = Modifier.padding(all = 20.dp)
        )
        TextButton(onClick = toSignUp) {
            Text(text = "Haven't got an account? Go here.")
        }
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = { userSignIn() }) {
            Text("Sign In")
        }
    }
}



package com.haiphong.mentalhealthapp.view.composables

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StandardTextField(
    value: String,
    label: String,
    onValueChange: (String) -> Unit,
    placeholder: String = "Enter your ${label.lowercase()}",
    isPassword: Boolean = false,
    isInvalidated: Boolean,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = { onValueChange(it) },
        label = { Text(text = label) },
        placeholder = { Text(text = placeholder) },
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = if (isPassword) KeyboardOptions(keyboardType = KeyboardType.Password)
        else KeyboardOptions(keyboardType = KeyboardType.Text),
        colors = if(isInvalidated) TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.Red,
            unfocusedBorderColor = Color.Red,
            focusedLabelColor = Color.Red,
            unfocusedLabelColor = Color.Red,
        ) else TextFieldDefaults.outlinedTextFieldColors()
    )

}
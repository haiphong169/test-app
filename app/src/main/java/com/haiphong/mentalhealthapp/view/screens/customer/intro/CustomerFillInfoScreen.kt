package com.haiphong.mentalhealthapp.view.screens.customer.intro



import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.haiphong.mentalhealthapp.R
import com.haiphong.mentalhealthapp.model.User
import com.haiphong.mentalhealthapp.view.util.viewmodel.FillInfoState
import com.haiphong.mentalhealthapp.view.composables.Dropdown
import com.haiphong.mentalhealthapp.view.composables.StandardTextField


val dates: List<Int> = (1..31).toList()
val months: List<Int> = (1..12).toList()
val years: List<Int> = (1940..2022).toList()
val genders = listOf("Male", "Female", "Other")


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomerFillInfoScreen(
    scrollState: ScrollState = rememberScrollState(),
    onNextButtonClicked: () -> Unit,
    fillInfoState: FillInfoState,
    clearForm: () -> Unit,
    setErrorMessage: (String) -> Unit,
    onFillUserInfo: (User) -> Unit,
    onNameChange: (String) -> Unit,
    onGenderChange: (String) -> Unit,
    onDateChange: (String) -> Unit,
    onMonthChange: (String) -> Unit,
    onYearChange: (String) -> Unit,
    onBioChange: (String) -> Unit,
    onAvatarPathChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {

    fun fillUserInfo() {
        // handle loi input
        if (fillInfoState.name == "") {
            setErrorMessage("Name is empty.")
            return
        } else if (fillInfoState.gender == "") {
            setErrorMessage("Gender is not chosen.")
            return
        } else if (fillInfoState.date == "") {
            setErrorMessage("Date is not chosen.")
            return
        } else if (fillInfoState.month == "") {
            setErrorMessage("Month is not chosen.")
            return
        } else if (fillInfoState.year == "") {
            setErrorMessage("Year is not chosen.")
            return
        } else if (fillInfoState.bio == "") {
            setErrorMessage("Please write something about yourself.")
            return
        }
        onFillUserInfo(
            User(
                name = fillInfoState.name,
                gender = fillInfoState.gender,
                date = fillInfoState.date,
                month = fillInfoState.month,
                year = fillInfoState.year,
                bio = fillInfoState.bio
            )
        )
        onNextButtonClicked()
        clearForm()

    }

    Column(
        modifier = modifier
            .padding(15.dp)
            .verticalScroll(state = scrollState)
    ) {
        Text(text = "Customer Fill Info Screen")
        Image(
            painter = painterResource(id = R.drawable.default_avatar),
            contentDescription = null,
            modifier = Modifier
                .clip(
                    CircleShape
                )
                .size(200.dp)
        )
        StandardTextField(
            value = fillInfoState.name,
            label = "Name",
            onValueChange = onNameChange,
            isInvalidated = false
        )
        Text("Gender:")
        Row() {
            genders.forEach { text ->
                RadioButton(
                    selected = text == fillInfoState.gender,
                    onClick = { onGenderChange(text) })
                Text(text = text)
            }
        }
        Text("Date of birth:")
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
            Dropdown(items = dates, item = fillInfoState.date, onValueChange = onDateChange)
            Dropdown(items = months, item = fillInfoState.month, onValueChange = onMonthChange)
            Dropdown(items = years, item = fillInfoState.year, onValueChange = onYearChange)
        }
        Text("Biography:")
        OutlinedTextField(
            value = fillInfoState.bio,
            onValueChange = onBioChange,
            label = { Text("Biography") },
            placeholder = { Text("Introduce about yourself...") },
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        if (fillInfoState.invalidated) Text(
            text = fillInfoState.errorMessage, color = Color.Red,
            fontSize = 16.sp,
            modifier = Modifier.padding(all = 20.dp)
        )
        Button(onClick = { fillUserInfo() }) {
            Text(text = "Sign Up")
        }
    }
}
package com.haiphong.mentalhealthapp.view.screens.specialist.profile

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.haiphong.mentalhealthapp.R
import com.haiphong.mentalhealthapp.view.screens.specialist.intro.dates
import com.haiphong.mentalhealthapp.view.screens.specialist.intro.genders
import com.haiphong.mentalhealthapp.view.screens.specialist.intro.months
import com.haiphong.mentalhealthapp.view.screens.specialist.intro.years
import com.haiphong.mentalhealthapp.view.composables.Dropdown
import com.haiphong.mentalhealthapp.view.composables.StandardTextField
import com.haiphong.mentalhealthapp.viewmodel.specialistProfile.SpecialistEditProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpecialistEditProfileScreen(
    onComplete: () -> Unit,
    editProfileViewModel: SpecialistEditProfileViewModel = viewModel(),
    scrollState: ScrollState = rememberScrollState()
) {
    val editProfileState by editProfileViewModel.specialist.collectAsState()
    var credential by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .padding(15.dp)
            .verticalScroll(state = scrollState)
    ) {
        Text(text = "Specialist Fill Info Screen")
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
            value = editProfileState.name,
            label = "Name",
            onValueChange = editProfileViewModel::onNameChange,
            isInvalidated = false
        )
        Text("Gender:")
        Row() {
            genders.forEach { text ->
                RadioButton(
                    selected = text == editProfileState.gender,
                    onClick = { editProfileViewModel.onGenderChange(text) })
                Text(text = text)
            }
        }
        Text("Date of birth:")
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
            Dropdown(
                items = dates,
                item = editProfileState.date,
                onValueChange = editProfileViewModel::onDateChange
            )
            Dropdown(
                items = months,
                item = editProfileState.month,
                onValueChange = editProfileViewModel::onMonthChange
            )
            Dropdown(
                items = years,
                item = editProfileState.year,
                onValueChange = editProfileViewModel::onYearChange
            )
        }
        Text("Biography:")
        OutlinedTextField(
            value = editProfileState.bio,
            onValueChange = editProfileViewModel::onBioChange,
            label = { Text("Biography") },
            placeholder = { Text("Introduce about yourself...") },
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row() {
            Text(text = "Price: ")
            Spacer(modifier = Modifier.width(5.dp))
            OutlinedTextField(
                value = editProfileState.pricePerSession,
                onValueChange = editProfileViewModel::onPriceChange,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.width(100.dp)
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(text = "VND per 1-hour session")
        }
        Text("Workplace:")
        StandardTextField(
            value = editProfileState.workplace,
            label = "Workplace",
            onValueChange = editProfileViewModel::onWorkplaceChange,
            isInvalidated = false
        )
        Row(modifier = Modifier.fillMaxWidth()) {
            StandardTextField(
                value = credential,
                label = "Credential",
                onValueChange = { credential = it },
                isInvalidated = false,
                modifier = Modifier.weight(1.0f)
            )
            IconButton(onClick = {
                editProfileViewModel.addCredentials(credential)
                credential = ""
            }) {
                Icon(Icons.Default.Add, contentDescription = "Add Credential")
            }
        }
        Column() {
            editProfileState.credentials.forEach { item ->
                Text(text = item)
            }
        }
//        Column() {
//            Row() {
//                daysOfWeek.forEach {
//                    Text(text = it, fontSize = 12.sp)
//                }
//            }
//            Spacer(modifier = Modifier.height(5.dp))
//            Text(text = "7:00")
//            for (i in 0..11) {
//                Row() {
//                    Box(modifier = Modifier.width(70.dp)) {
//                        Text("${i + 8}:00")
//                    }
//                    for (j in 0..6) {
//                        ClickableBox(
//                            isChecked = editProfileState.availableTimeSlots[i][j],
//                            check = {
//                                editProfileViewModel.addAvailableTimeSlot(i, j)
//
//                            },
//                            uncheck = { editProfileViewModel.removeTimeSlot(i, j) })
//                    }
//                }
//            }
//        }
        Button(onClick = { editProfileViewModel.submitForm(onComplete) }) {
            Text("Submit Form")
        }
    }

}
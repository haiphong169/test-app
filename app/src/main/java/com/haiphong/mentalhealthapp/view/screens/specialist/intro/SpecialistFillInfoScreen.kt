package com.haiphong.mentalhealthapp.view.screens.specialist.intro

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
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.haiphong.mentalhealthapp.R
import com.haiphong.mentalhealthapp.view.composables.Dropdown
import com.haiphong.mentalhealthapp.view.composables.StandardTextField
import com.haiphong.mentalhealthapp.view.composables.specialist.ScheduleTable
import com.haiphong.mentalhealthapp.viewmodel.specialistIntro.SpecialistFillInfoViewModel

val dates: List<Int> = (1..31).toList()
val months: List<Int> = (1..12).toList()
val years: List<Int> = (1940..2022).toList()
val genders = listOf("Male", "Female", "Other")


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpecialistFillInfoScreen(
    viewModel: SpecialistFillInfoViewModel = viewModel(),
    scrollState: ScrollState = rememberScrollState(),
    onCompleteForm: () -> Unit
) {
    val fillInfoState by viewModel.fillInfoState.collectAsState()
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
            value = fillInfoState.name,
            label = "Name",
            onValueChange = viewModel::onNameChange,
            isInvalidated = false
        )
        Text("Gender:")
        Row() {
            genders.forEach { text ->
                RadioButton(
                    selected = text == fillInfoState.gender,
                    onClick = { viewModel.onGenderChange(text) })
                Text(text = text)
            }
        }
        Text("Date of birth:")
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
            Dropdown(
                items = dates,
                item = fillInfoState.date,
                onValueChange = viewModel::onDateChange
            )
            Dropdown(
                items = months,
                item = fillInfoState.month,
                onValueChange = viewModel::onMonthChange
            )
            Dropdown(
                items = years,
                item = fillInfoState.year,
                onValueChange = viewModel::onYearChange
            )
        }
        Text("Biography:")
        OutlinedTextField(
            value = fillInfoState.bio,
            onValueChange = viewModel::onBioChange,
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
                value = fillInfoState.pricePerSession.toString(),
                onValueChange = viewModel::onPriceChange,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.width(100.dp)
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(text = "VND per 1-hour session")
        }
        Text("Workplace:")
        StandardTextField(
            value = fillInfoState.workplace,
            label = "Workplace",
            onValueChange = viewModel::onWorkplaceChange,
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
                viewModel.addCredentials(credential)
                credential = ""
            }) {
                Icon(Icons.Default.Add, contentDescription = "Add Credential")
            }
        }
        Column() {
            fillInfoState.credentials.forEach { item ->
                Text(text = item)
            }
        }

        ScheduleTable(
            availableTimeSlots = fillInfoState.availableTimeSlots,
            addTimeSlot = viewModel::addAvailableTimeSlot,
            removeTimeSlot = viewModel::removeTimeSlot
        )
        // schedule
//        Box(
//            contentAlignment = Alignment.Center, modifier = Modifier
//                .height(400.dp)
//                .verticalScroll(state = rememberScrollState())
//        ) {
//            LazyRow {
//                items(7) { rowNum ->
//                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
//                        Text(text = convert(rowNum), fontSize = 12.sp)
//                        fillInfoState.availableTimeSlots[rowNum].forEachIndexed { columnNum, isAvailable ->
//                            SettingSlot(
//                                isAvailable = isAvailable,
//                                columnNum = columnNum,
//                                addTimeSlot = {
//                                    viewModel.addAvailableTimeSlot(
//                                        column = columnNum,
//                                        row = rowNum
//                                    )
//                                }) {
//                                viewModel.removeTimeSlot(
//                                    column = columnNum,
//                                    row = rowNum
//                                )
//                            }
//                        }
//                    }
//                }
//            }
//        }
        // schedule

        Button(onClick = {
            viewModel.sendRequest(onCompleteForm)
        }) {
            Text(text = "Submit Form")
        }
    }
}
package com.haiphong.mentalhealthapp.view.screens.customer.bookappointments

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.haiphong.mentalhealthapp.R
import com.haiphong.mentalhealthapp.viewmodel.bookAppointment.SpecialistInfoViewModel

@Composable
fun SpecialistInfoScreen(
    onComplete: () -> Unit,
    infoViewModel: SpecialistInfoViewModel = viewModel()
) {
    val specialist by infoViewModel.specialist.collectAsState()
    val table by infoViewModel.table.collectAsState()
    val dates = infoViewModel.dateList

    /*
    fun alreadyScheduled(date: String, session: String): Boolean {
        for (pair in specialist.scheduledSlots) {
            if (date == pair.first && session == pair.second) {
                return true
            }
        }
        return false
    }
    */

    Column(
        horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
            .height(400.dp)
            .verticalScroll(state = rememberScrollState())
    ) {
        LazyRow {
            items(14) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "${dates[it].month} ${dates[it].dayOfMonth}", fontSize = 12.sp)
                    Text(text = dates[it].dayOfWeek.toString(), fontSize = 12.sp)
                    table[it].forEachIndexed { index, isAvailable ->
                        TimeSlot(
                            isAvailable = isAvailable,
                            slot = index,
                            bookAppointment = {
                                infoViewModel.createAppointment(
                                    onComplete = onComplete,
                                    date = dates[it],
                                    session = "${"%02d".format(index + 7)}:00 -> ${
                                        "%02d".format(
                                            index + 8
                                        )
                                    }:00"
                                )
                            })
                    }
                }
            }
        }
    }
}

@Composable
fun TimeSlot(isAvailable: Int, slot: Int, bookAppointment: () -> Unit) {
    Box(
        modifier = Modifier
            .size(100.dp)
            .clickable { if (isAvailable == 1) bookAppointment() },
        contentAlignment = Alignment.Center
    ) {
        when (isAvailable) {
            1 -> {
                Card(
                    modifier = Modifier.padding(6.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFADD8E6)
                    ), elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Text(
                        text = "${slot + 7}:00\n - \n${slot + 8}:00",
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center
                    )
                }
            }
            0 -> {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_remove_24),
                    contentDescription = "empty"
                )
            }
            2 -> {
                Card(
                    modifier = Modifier.padding(6.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFADD8E6)
                    ), elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Text(
                        text = "Already scheduled.",
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center
                    )
                }
            }
            3 -> {
                Card(
                    modifier = Modifier.padding(6.dp).size(80.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.LightGray
                    ), elevation = CardDefaults.cardElevation(4.dp)
                ) {

                }
            }
            4 -> {
                Card(
                    modifier = Modifier.padding(6.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFADD8E6)
                    ), elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Text(
                        text = "You have scheduled this session for this week.",
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

/*
LazyRow {
    items(infoViewModel.dateList) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "${it.month} ${it.dayOfMonth}", fontSize = 12.sp)
            Text(text = it.dayOfWeek.toString(), fontSize = 12.sp)
            specialist.availableTimeSlots[it.dayOfWeek.value - 1].forEachIndexed { index, isAvailable ->
                TimeSlot(
                    isAvailable = if (alreadyScheduled(
                            "${it.month} ${it.dayOfMonth}",
                            "${"%02d".format(index + 7)}:00 -> ${
                                "%02d".format(
                                    index + 8
                                )
                            }:00"
                        )
                    ) 0 else isAvailable,
                    slot = index,
                    bookAppointment = {
                        infoViewModel.createAppointment(
                            onComplete = onComplete,
                            date = it,
                            session = "${"%02d".format(index + 7)}:00 -> ${
                                "%02d".format(
                                    index + 8
                                )
                            }:00"
                        )
                    })
            }
        }
    }
}
*/
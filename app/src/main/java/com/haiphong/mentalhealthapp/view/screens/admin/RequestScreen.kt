package com.haiphong.mentalhealthapp.view.screens.admin

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.haiphong.mentalhealthapp.view.composables.specialist.SpecialistInfo
import com.haiphong.mentalhealthapp.viewmodel.request.RequestViewModel


//todo: test this screen
@Composable
fun RequestScreen(
    onComplete: () -> Unit,
    viewModel: RequestViewModel = viewModel(),
    scrollState: ScrollState = rememberScrollState()
) {
    val specialist by viewModel.specialist.collectAsState()

    Column(
        modifier = Modifier.verticalScroll(state = scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SpecialistInfo(specialist = specialist)
//        Box(
//            contentAlignment = Alignment.Center, modifier = Modifier
//                .height(400.dp)
//                .verticalScroll(state = rememberScrollState())
//        ) {
//            LazyRow {
//                items(7) { rowNum ->
//                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
//                        Text(text = convert(rowNum), fontSize = 12.sp)
//                        specialist.availableTimeSlots[rowNum].forEachIndexed { columnNum, isAvailable ->
//                            SettingSlot(
//                                isAvailable = isAvailable,
//                                columnNum = columnNum,
//                            )
//                        }
//                    }
//                }
//            }
//        }
        Row() {
            Button(onClick = { viewModel.acceptRequest(onComplete) }) {
                Text("Accept")
            }
            Button(onClick = { viewModel.declineRequest(onComplete) }) {
                Text("Decline")
            }
        }
    }
}
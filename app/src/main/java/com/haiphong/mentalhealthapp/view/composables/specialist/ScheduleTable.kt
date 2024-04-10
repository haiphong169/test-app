package com.haiphong.mentalhealthapp.view.composables.specialist

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.haiphong.mentalhealthapp.R

fun convert(day: Int): String {
    return when (day) {
        0 -> "MON"
        1 -> "TUE"
        2 -> "WED"
        3 -> "THU"
        4 -> "FRI"
        5 -> "SAT"
        6 -> "SUN"
        else -> "N/A"
    }
}

@Composable
fun ScheduleTable(
    availableTimeSlots: Array<Array<Int>>,
    addTimeSlot: (Int, Int) -> Unit = { _, _ -> },
    removeTimeSlot: (Int, Int) -> Unit = { _, _ -> }
) {
    Box(
        contentAlignment = Alignment.Center, modifier = Modifier
            .height(400.dp)
            .verticalScroll(state = rememberScrollState())
    ) {
        LazyRow {
            items(7) { rowNum ->
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = convert(rowNum))
                    availableTimeSlots[rowNum].forEachIndexed { columnNum, isAvailable ->
                        SettingSlot(
                            isAvailable = isAvailable,
                            slot = columnNum,
                            addTimeSlot = {
                                addTimeSlot(
                                    columnNum,
                                    rowNum
                                )
                            }) {
                            removeTimeSlot(
                                columnNum,
                                rowNum
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SettingSlot(
    isAvailable: Int,
    slot: Int,
    addTimeSlot: () -> Unit = {},
    removeTimeSlot: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .size(100.dp)
            .clickable {
                if (isAvailable == 0) {
                    addTimeSlot()
                } else {
                    removeTimeSlot()
                }
            }, contentAlignment = Alignment.Center
    ) {
        if (isAvailable == 1) {
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
        } else {
            Icon(
                painter = painterResource(id = R.drawable.baseline_remove_24),
                contentDescription = "empty"
            )
        }
    }
}
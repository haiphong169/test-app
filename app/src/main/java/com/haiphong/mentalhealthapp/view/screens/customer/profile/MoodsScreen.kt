package com.haiphong.mentalhealthapp.view.screens.customer.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.haiphong.mentalhealthapp.model.Mood
import com.haiphong.mentalhealthapp.model.valueToIcon
import com.haiphong.mentalhealthapp.viewmodel.profile.MoodsViewModel
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

@Composable
fun MoodsScreen(moodsViewModel: MoodsViewModel = viewModel()) {
    val moodsList by moodsViewModel.moodsList.collectAsState()

    LazyColumn {
        items(moodsList) {
            MoodListItem(mood = it)
        }
    }
}

@Composable
fun MoodListItem(mood: Mood) {
    val ldt =
        LocalDateTime.ofInstant(Instant.ofEpochSecond(mood.epochSecond), ZoneId.systemDefault())

    Card(
        modifier = Modifier.padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize()
        ) {
            Text(
                text = "${ldt.month}, ${ldt.dayOfMonth}, ${ldt.hour}, ${ldt.minute}",
                style = MaterialTheme.typography.labelLarge
            )
            Icon(
                painter = painterResource(id = valueToIcon(mood.value)),
                contentDescription = mood.value.toString()
            )
        }
    }
}
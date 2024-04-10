package com.haiphong.mentalhealthapp.view.composables.specialist

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.haiphong.mentalhealthapp.R
import com.haiphong.mentalhealthapp.model.Specialist

@Composable
fun SpecialistInfo(specialist: Specialist) {
    Card(
        modifier = Modifier
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(modifier = Modifier.padding(6.dp)) {
            Image(
                painter = painterResource(id = R.drawable.default_avatar),
                contentDescription = "avatar",
                modifier = Modifier
                    .clip(
                        CircleShape
                    )
                    .size(200.dp)
                    .align(alignment = Alignment.CenterHorizontally),
                contentScale = ContentScale.Crop
            )
            Text(
                text = specialist.name,
                modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(30.dp))
            Text(text = "Gender: ${specialist.gender}", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Date of birth: ${specialist.date}/${specialist.month}/${specialist.year}",
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "Bio:", style = MaterialTheme.typography.titleLarge)
            Text(text = specialist.bio)
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Workplace: ${specialist.workplace}",
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text("Credentials: ", style = MaterialTheme.typography.titleLarge)
            Row(modifier = Modifier.padding(6.dp)) {
                specialist.credentials.forEach {
                    Card(
                        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Text(
                            text = it,
                            modifier = Modifier.padding(10.dp),
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Price per 1-hour session: ${specialist.pricePerSession}VND",
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(20.dp))
            ScheduleTable(availableTimeSlots = specialist.availableTimeSlots)
        }
    }
}
package com.haiphong.mentalhealthapp.view.composables.customer

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.haiphong.mentalhealthapp.R
import com.haiphong.mentalhealthapp.model.Specialist

@Composable
fun SpecialistListItem(specialist: Specialist, toSpecialist: (String) -> Unit) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .clickable { toSpecialist(specialist.specialistId) },
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter = painterResource(id = R.drawable.default_avatar),
                contentDescription = "avatar",
                modifier = Modifier
                    .clip(
                        CircleShape
                    )
                    .size(50.dp),
                contentScale = ContentScale.Crop
            )
            Column {
                Text(text = specialist.name, style = MaterialTheme.typography.titleLarge)
                Text(text = specialist.bio, style = MaterialTheme.typography.labelLarge)
            }
        }
    }
}
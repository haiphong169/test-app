package com.haiphong.mentalhealthapp.view.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.haiphong.mentalhealthapp.model.Appointment

@Composable
fun AppointmentListItem(appointment: Appointment, toAppointment: (String) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { toAppointment(appointment.appointmentId) },
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Text(text = appointment.date, style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(2.dp))
        Text(text = appointment.dayOfWeek, style = MaterialTheme.typography.labelLarge)
        Spacer(modifier = Modifier.height(2.dp))
        Text(text = appointment.session, style = MaterialTheme.typography.bodyLarge)
    }
}
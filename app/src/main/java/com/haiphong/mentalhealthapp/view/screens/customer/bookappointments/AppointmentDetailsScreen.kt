package com.haiphong.mentalhealthapp.view.screens.customer.bookappointments

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.haiphong.mentalhealthapp.view.composables.customer.CustomerInfo
import com.haiphong.mentalhealthapp.view.composables.specialist.SpecialistInfo
import com.haiphong.mentalhealthapp.viewmodel.bookAppointment.AppointmentDetailsViewModel

@Composable
fun AppointmentDetailsScreen(
    appointmentDetailsViewModel: AppointmentDetailsViewModel = viewModel(),
    onDelete: () -> Unit,
    isCustomerVisiting: Boolean
) {
    val appointmentState by appointmentDetailsViewModel.appointmentState.collectAsState()
    val appointment = appointmentState.appointment
    val specialist = appointmentState.specialist
    val customer = appointmentState.customer

    Column(modifier = Modifier.verticalScroll(state = rememberScrollState())) {
        // appointment detail
        Row {
            Card(
                modifier = Modifier
                    .padding(8.dp)
                    .weight(1.0f),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
            ) {
                Text(
                    text = appointment.date,
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = appointment.dayOfWeek,
                    style = MaterialTheme.typography.labelLarge
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = appointment.session,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Spacer(modifier = Modifier.width(20.dp))

            IconButton(onClick = {
                if (isCustomerVisiting) {
                    appointmentDetailsViewModel.changeOpenDialog(true)
                }
            }) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Cancel Appointment")
            }
        }

        // specialist or customer info
        if (isCustomerVisiting) {
            SpecialistInfo(specialist = specialist)
        } else {
            CustomerInfo(customer = customer)
        }

        if (appointmentState.openDialog) {
            AlertDialogExample(
                onDismissRequest = { appointmentDetailsViewModel.changeOpenDialog(false) },
                onConfirmation = {
                    appointmentDetailsViewModel.changeOpenDialog(false)
                    appointmentDetailsViewModel.cancelAppointment { onDelete() }
                },
                dialogTitle = "Warning",
                dialogText = "Do you really want to cancel the appointment?",
                icon = Icons.Default.Delete
            )
        }
    }
}

@Composable
fun AlertDialogExample(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector,
) {
    AlertDialog(
        icon = {
            Icon(icon, contentDescription = "Example Icon")
        },
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Dismiss")
            }
        }
    )
}

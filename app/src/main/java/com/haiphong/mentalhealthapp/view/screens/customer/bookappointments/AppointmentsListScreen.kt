package com.haiphong.mentalhealthapp.view.screens.customer.bookappointments

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.haiphong.mentalhealthapp.view.composables.AppointmentListItem
import com.haiphong.mentalhealthapp.viewmodel.bookAppointment.AppointmentsListViewModel

@Composable
fun AppointmentsListScreen(
    appointmentsListViewModel: AppointmentsListViewModel = viewModel(),
    toAppointment: (String) -> Unit
) {
    val appointmentsList by appointmentsListViewModel.appointmentList.collectAsState()

    LazyColumn {
        items(appointmentsList) {
            AppointmentListItem(appointment = it, toAppointment = toAppointment)
        }
    }
}
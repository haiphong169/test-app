package com.haiphong.mentalhealthapp.view.screens.specialist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.haiphong.mentalhealthapp.UserAuthentication
import com.haiphong.mentalhealthapp.view.composables.AppointmentListItem
import com.haiphong.mentalhealthapp.viewmodel.specialistHome.SpecialistHomeViewModel

@Composable
fun SpecialistHomeScreen(
    onSignOut: () -> Unit,
    toProfile: () -> Unit,
    toAppointment: (String) -> Unit,
    homeViewModel: SpecialistHomeViewModel = viewModel()
) {
    val appointmentList by homeViewModel.appointmentList.collectAsState()

    Column() {
        Box() {
            LazyColumn {
                items(appointmentList) {
                    AppointmentListItem(appointment = it, toAppointment = toAppointment)
                }
            }
        }
        Button(onClick = { UserAuthentication.signOut(onSignOut) }) {
            Text(text = "Sign Out")
        }
        Button(onClick = { toProfile() }) {
            Text(text = "To Profile")
        }
    }
}
package com.haiphong.mentalhealthapp.view.screens.customer.bookappointments

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.haiphong.mentalhealthapp.Route
import com.haiphong.mentalhealthapp.view.composables.BottomAppBar
import com.haiphong.mentalhealthapp.view.composables.customer.SpecialistListItem
import com.haiphong.mentalhealthapp.viewmodel.bookAppointment.BookAppointmentsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookAppointmentsScreen(
    navController: NavController,
    viewModel: BookAppointmentsViewModel = viewModel()
) {
    val specialistsList by viewModel.specialistsList.collectAsState()

    Scaffold(bottomBar = { BottomAppBar(navController = navController) }) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            items(specialistsList) {
                SpecialistListItem(specialist = it, toSpecialist = { id ->
                    navController.navigate("${Route.SpecialistInfo.name}/$id")
                })
            }
            item {
                Button(onClick = { navController.navigate(Route.AppointmentsList.name) }) {
                    Text(text = "Appointments List")
                }
            }
        }
    }
}
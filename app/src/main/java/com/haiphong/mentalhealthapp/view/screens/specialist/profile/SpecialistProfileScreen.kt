package com.haiphong.mentalhealthapp.view.screens.specialist.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.haiphong.mentalhealthapp.view.composables.specialist.SpecialistInfo
import com.haiphong.mentalhealthapp.viewmodel.specialistProfile.SpecialistProfileViewModel

@Composable
fun SpecialistProfileScreen(
    toEdit: () -> Unit,
    profileViewModel: SpecialistProfileViewModel = viewModel(),
) {
    val specialist by profileViewModel.specialist.collectAsState()

    Column(modifier = Modifier.verticalScroll(state = rememberScrollState())) {
        SpecialistInfo(specialist = specialist)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
                .clickable { toEdit() }
        ) {
            Row(modifier = Modifier.padding(6.dp).height(50.dp), verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Edit, contentDescription = "Edit Profile")
                Spacer(modifier = Modifier.width(10.dp))
                androidx.compose.material3.Text(text = "Edit", style = MaterialTheme.typography.titleLarge)
            }
        }
    }
}

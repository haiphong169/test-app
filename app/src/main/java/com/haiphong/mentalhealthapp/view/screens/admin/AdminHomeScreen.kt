package com.haiphong.mentalhealthapp.view.screens.admin

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.haiphong.mentalhealthapp.view.composables.admin.RequestListItem


@Composable
fun AdminHomeScreen(
    viewModel: com.haiphong.mentalhealthapp.viewmodel.adminHome.AdminHomeViewModel = viewModel(),
    toRequest: (String) -> Unit,
    signOut: () -> Unit
) {
    val requestList by viewModel.requestsList.collectAsState()

    LazyColumn {
        items(requestList) {
            RequestListItem(request = it, toRequest = toRequest)
        }
        item {
            Button(onClick = { signOut() }) {
                Text(text = "Sign Out")
            }
        }
    }
}
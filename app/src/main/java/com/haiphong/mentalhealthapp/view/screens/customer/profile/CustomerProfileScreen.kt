package com.haiphong.mentalhealthapp.view.screens.customer.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.haiphong.mentalhealthapp.UserAuthentication
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.haiphong.mentalhealthapp.view.composables.BottomAppBar
import com.haiphong.mentalhealthapp.view.composables.customer.CustomerInfo
import com.haiphong.mentalhealthapp.viewmodel.profile.CustomerProfileViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomerProfileScreen(
    onSignOut: () -> Unit,
    toEditScreen: () -> Unit,
    toMoodsScreen: () -> Unit,
    viewModel: CustomerProfileViewModel = viewModel(),
    navController: NavController
) {
    val profileState by viewModel.profileState.collectAsState()

    Scaffold(bottomBar = { BottomAppBar(navController = navController) }) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
        ) {
            CustomerInfo(customer = profileState)

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
                    .clickable { toEditScreen() }
            ) {
                Row(
                    modifier = Modifier
                        .padding(6.dp)
                        .height(50.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit Profile")
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(text = "Edit", style = MaterialTheme.typography.titleLarge)
                }
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
                    .clickable { UserAuthentication.signOut(onSignOut) }
            ) {
                Row(
                    modifier = Modifier
                        .padding(6.dp)
                        .height(50.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.ExitToApp, contentDescription = "Sign Out")
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(text = "Sign Out", style = MaterialTheme.typography.titleLarge)
                }
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
                    .clickable { toMoodsScreen() }
            ) {
                Row(
                    modifier = Modifier
                        .padding(6.dp)
                        .height(50.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.List, contentDescription = "To Moods Screen")
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(text = "To Moods Screen", style = MaterialTheme.typography.titleLarge)
                }
            }
        }
    }
}
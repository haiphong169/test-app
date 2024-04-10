package com.haiphong.mentalhealthapp.view.screens.customer.content


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.haiphong.mentalhealthapp.model.topics
import com.haiphong.mentalhealthapp.model.valueToIcon
import com.haiphong.mentalhealthapp.view.composables.BottomAppBar
import com.haiphong.mentalhealthapp.view.composables.TopicCard
import com.haiphong.mentalhealthapp.viewmodel.content.CustomerHomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomerHomeScreen(
    toTopic: (String) -> Unit,
    navController: NavController,
    homeViewModel: CustomerHomeViewModel = viewModel(
        factory = CustomerHomeViewModel.Factory
    )
) {
    val openDialog by homeViewModel.openDialog.collectAsState()


    Scaffold(bottomBar = { BottomAppBar(navController = navController) }) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .padding(8.dp)
        ) {
            items(items = topics, key = { topic -> topic.name }) { topic ->
                TopicCard(topic = topic, toTopic = toTopic)
            }
            item {
                Spacer(modifier = Modifier.height(30.dp))
            }
            if (openDialog) {
                item {
                    Dialog(onDismissRequest = { homeViewModel.closeDialog() }) {
                        Card(
                            modifier = Modifier
                                .height(225.dp)
                                .width(450.dp)
                                .padding(16.dp),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp)
                            ) {
                                Text(
                                    text = "How are you feeling right now?",
                                    style = MaterialTheme.typography.headlineSmall
                                )
                                Spacer(modifier = Modifier.height(30.dp))
                                Row() {
                                    (1..5).forEach { value ->
                                        MoodItem(value = value, onClick = {
                                            homeViewModel.addMood(value)
                                        })
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun MoodItem(value: Int, onClick: () -> Unit) {
    IconButton(onClick = onClick, modifier = Modifier.width(50.dp)) {
        Icon(
            painter = painterResource(id = valueToIcon(value)),
            contentDescription = value.toString()
        )
    }
}
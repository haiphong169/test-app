package com.haiphong.mentalhealthapp.view.screens.customer.journal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.haiphong.mentalhealthapp.viewmodel.journal.CustomerJournalDetailViewModel

@Composable
fun CustomerJournalDetailScreen(
    toEdit: (String) -> Unit,
    onDelete: () -> Unit,
    viewModel: CustomerJournalDetailViewModel = viewModel()
) {
    val journalState by viewModel.journalState.collectAsState()
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(8.dp)) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = journalState.title, fontSize = 28.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.width(20.dp))
            Box {
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(Icons.Default.MoreVert, contentDescription = "Edit or Delete")
                }
                DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    DropdownMenuItem(
                        text = { Text("Edit") },
                        onClick = {
                            expanded = false
                            toEdit(journalState.journalId) })
                    DropdownMenuItem(
                        text = { Text(text = "Delete", color = Color.Red) },
                        onClick = {
                            expanded = false
                            viewModel.deleteJournal(onDelete)
                        })
                }
            }
        }
        Text(
            text = journalState.fullDate(),
            fontSize = 10.sp,
            fontStyle = FontStyle.Italic,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = journalState.content, fontSize = 16.sp)
    }
}
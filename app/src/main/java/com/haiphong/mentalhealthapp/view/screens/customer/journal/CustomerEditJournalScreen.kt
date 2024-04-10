package com.haiphong.mentalhealthapp.view.screens.customer.journal

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.haiphong.mentalhealthapp.viewmodel.journal.CustomerEditJournalViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomerEditJournalScreen(
    onSave: (String) -> Unit,
    viewModel: CustomerEditJournalViewModel = viewModel()
) {
    val editJournalState by viewModel.editJournalState.collectAsState()

    Column(modifier = Modifier.padding(16.dp)) {
        Row() {
            TextField(
                value = editJournalState.title,
                onValueChange = { viewModel.onTitleChange(it) },
                singleLine = true,
                modifier = Modifier
                    .weight(1f),
                colors = TextFieldDefaults.textFieldColors(
                    placeholderColor = Color.Gray,
                    containerColor = Color(0xFAFAFA)
                )
            )
            Spacer(modifier = Modifier.width(20.dp))
            Button(onClick = {
                viewModel.updateJournal(onSave)
            }) {
                Text(text = "Save")
            }
        }
        Spacer(modifier = Modifier.height(30.dp))
        TextField(
            value = editJournalState.content,
            onValueChange = {
                viewModel.onContentChange(it)
            },
            colors = TextFieldDefaults.textFieldColors(
                placeholderColor = Color.Gray,
                containerColor = Color(0xFAFAFA)
            ),
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        )
    }
}

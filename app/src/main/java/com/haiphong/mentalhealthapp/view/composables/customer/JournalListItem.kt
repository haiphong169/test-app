package com.haiphong.mentalhealthapp.view.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.haiphong.mentalhealthapp.model.Journal

@Composable
fun JournalListItem(journal: Journal, toJournal: (String) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
            .clickable { toJournal(journal.journalId) }
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()

            ) {
                Text(text = journal.title)
                Text(text = journal.writtenAt())
            }
            Text(text = journal.contentOnCard())
        }
    }
}
package com.haiphong.mentalhealthapp.view.screens.customer.journal

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.haiphong.mentalhealthapp.view.composables.BottomAppBar
import com.haiphong.mentalhealthapp.view.composables.JournalListItem
import com.haiphong.mentalhealthapp.viewmodel.journal.JournalListState

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun CustomerJournalListScreen(
    journalListState: JournalListState,
    onAddJournal: () -> Unit,
    toJournal: (String) -> Unit,
    navController: NavController
) {
    if (journalListState.isLoading) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
        }
    } else {
        val grouped = journalListState.journalList.groupBy { it.fullDateWithoutHour() }
        Scaffold(
            bottomBar = { BottomAppBar(navController = navController) },
            floatingActionButton = {
                FloatingActionButton(onClick = onAddJournal) {
                    Icon(Icons.Rounded.Edit, contentDescription = "Add new journal")
                }
            }) { innerPadding ->
            LazyColumn(modifier = Modifier.padding(innerPadding).padding(16.dp)) {
                grouped.forEach { (date, journals) ->
                    stickyHeader {
                        Text(text = date)
                    }

                    items(items = journals, key = { journal -> journal.journalId }) { journal ->
                        JournalListItem(journal = journal, toJournal = toJournal)
                    }

                }
            }
        }
    }
}

//items(
//items = journalListState.journalList,
//key = { journal ->
//    journal.journalId
//},
//) { journal ->
//    JournalListItem(journal = journal, toJournal = toJournal)
//}
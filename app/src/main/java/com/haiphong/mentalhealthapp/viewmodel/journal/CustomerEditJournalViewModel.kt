package com.haiphong.mentalhealthapp.viewmodel.journal

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.google.firebase.Timestamp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.haiphong.mentalhealthapp.model.Journal
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

// todo: add error message

class CustomerEditJournalViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {
    private val _editJournalState = MutableStateFlow(Journal())
    val editJournalState = _editJournalState.asStateFlow()

    private val journalId = checkNotNull(savedStateHandle["journalId"])

    init {
        getJournal()
    }

    fun onTitleChange(newTitle: String) {
        _editJournalState.update {
            it.copy(
                title = newTitle
            )
        }
    }

    fun onContentChange(newContent: String) {
        _editJournalState.update {
            it.copy(
                content = newContent
            )
        }
    }

    fun getJournal() {
        val db = Firebase.firestore
        db.collection("notes").document(journalId as String).get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val data = document.data
                    _editJournalState.update {
                        it.copy(
                            journalId = document.id,
                            title = data!!["title"].toString(),
                            content = data["content"].toString(),
                            writtenBy = data["writtenBy"].toString(),
                            date = data["date"] as Timestamp
                        )
                    }
                } else {
                    Log.d("Ger Journal for edit", "Can't find the journal")
                }
            }
            .addOnFailureListener { e -> Log.d("Get Journal For Edit", "failed with", e) }
    }

    fun updateJournal(onSave: (String) -> Unit) {
        val db = Firebase.firestore
        db.collection("notes").document(journalId as String).set(editJournalState.value.toHashMap())
            .addOnSuccessListener { onSave(journalId) }
            .addOnFailureListener { e -> Log.d("Edit Journal", "Failed with", e) }
    }
}
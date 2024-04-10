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

class CustomerJournalDetailViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {
    private val _journalState = MutableStateFlow(Journal())
    val journalState = _journalState.asStateFlow()

    private val journalId = checkNotNull(savedStateHandle["journalId"])

    init {
        getJournal()
    }

    private fun getJournal() {
        val db = Firebase.firestore
        db.collection("notes").document(journalId as String).get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val data = document.data
                    _journalState.update {
                        it.copy(
                            journalId = journalId,
                            content = data!!["content"].toString(),
                            title = data["title"].toString(),
                            writtenBy = data["writtenBy"].toString(),
                            date = data["date"] as Timestamp
                        )
                    }
                } else {
                    Log.d("Get Journal", "No journal with such id found")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("Get Journal", "get failed with", exception)
            }
    }

    fun deleteJournal(onDelete: () -> Unit) {
        val db = Firebase.firestore
        db.collection("notes").document(journalId as String).delete()
            .addOnSuccessListener { onDelete() }
            .addOnFailureListener { exception ->
                Log.d(
                    "Delete Journal",
                    "Delete failed with",
                    exception
                )
            }
    }
}
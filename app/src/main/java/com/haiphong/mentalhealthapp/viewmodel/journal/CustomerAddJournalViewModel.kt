package com.haiphong.mentalhealthapp.viewmodel.journal

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.haiphong.mentalhealthapp.UserAuthentication
import com.haiphong.mentalhealthapp.model.Journal
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class AddJournalState(
    val title: String = "",
    val content: String = "",
    val errorMessage: String = "",
    val isInvalidated: Boolean = false
)

class CustomerAddJournalViewModel : ViewModel() {

    private val _addJournalState = MutableStateFlow(AddJournalState())
    val addJournalState = _addJournalState.asStateFlow()

    fun onTitleChange(newTitle: String) {
        _addJournalState.update {
            it.copy(
                title = newTitle
            )
        }
    }

    fun onContentChange(newContent: String) {
        _addJournalState.update {
            it.copy(
                content = newContent
            )
        }
    }

    fun setErrorMessage(message: String) {
        _addJournalState.update {
            it.copy(
                errorMessage = message,
                isInvalidated = true
            )
        }
    }

    fun addJournal(onSave: () -> Unit) {
        val db = Firebase.firestore
        val journal = Journal(
            content = addJournalState.value.content,
            title = addJournalState.value.title,
            writtenBy = UserAuthentication.getUid()
        )
        db.collection("notes").add(journal.toHashMap())
            .addOnSuccessListener { onSave() }
            .addOnFailureListener { e -> Log.d("Add Journal", "Failed", e) }
    }
}

package com.haiphong.mentalhealthapp.viewmodel.journal

import android.icu.text.TimeZoneFormat
import android.util.Log
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import com.google.gson.Gson
import com.haiphong.mentalhealthapp.UserAuthentication
import com.haiphong.mentalhealthapp.model.Journal
import com.haiphong.mentalhealthapp.view.screens.customer.journal.BaseItemNote
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.io.File
import java.time.LocalDateTime
import java.time.ZoneOffset

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

    fun addJournal(
        items: MutableList<BaseItemNote<String>>,
        onSave: () -> Unit
    ) {
        val uploadItems = mutableListOf<BaseItemNote<String>>()
        viewModelScope.launch {
            items.forEach { item ->
                when (item) {
                    is BaseItemNote.Image -> {
                        //save to cloud storage firebase and return url
                        val storageRef = Firebase.storage.getReference("/notes/${LocalDateTime.now().toEpochSecond(
                            ZoneOffset.UTC)}")
                        val uploadTask =  storageRef.putFile(item.url.toUri()).await()
                        if(uploadTask.task.isSuccessful){
                            val downloadUrl = storageRef.downloadUrl.await()
                            uploadItems.add(BaseItemNote.Image(downloadUrl.toString()))
                        }
                    }
                    is BaseItemNote.Text -> {
                        uploadItems.add(item)
                    }
                }
            }
            _addJournalState.value = addJournalState.value.copy(content = Gson().toJson(uploadItems))
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
}

package com.haiphong.mentalhealthapp.viewmodel.journal

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.Timestamp
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.haiphong.mentalhealthapp.UserAuthentication
import com.haiphong.mentalhealthapp.model.Journal
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class JournalListState(
    val journalList: List<Journal> = listOf(),
    var isLoading: Boolean = false
)

class CustomerJournalListViewModel : ViewModel() {
    private val _journalState = MutableStateFlow(JournalListState())
    val journalState = _journalState.asStateFlow()

    init {
        getData()
    }

    private fun setIsLoading(isLoading: Boolean) {
        _journalState.update { currentState ->
            currentState.copy(
                isLoading = isLoading
            )
        }
    }

    fun getData() {
        setIsLoading(true)
        val db = Firebase.firestore
        db.collection("notes").whereEqualTo("writtenBy", UserAuthentication.getUid())
            .orderBy("date", Query.Direction.DESCENDING).get().addOnSuccessListener { document ->
                setIsLoading(false)
                if (document == null) {
                    Log.d("GetJournalsList", "List is empty.")
                } else {
                    val docList = document.documents
                    val list = mutableListOf<Journal>()
                    docList.forEach {
                        list.add(
                            Journal(
                                journalId = it.id,
                                title = it.data!!["title"].toString(),
                                content = it.data!!["content"].toString(),
                                writtenBy = it.data!!["writtenBy"].toString(),
                                date = it.data!!["date"] as Timestamp
                            )
                        )
                    }
                    _journalState.update { currentState ->
                        currentState.copy(
                            journalList = list
                        )
                    }
                }
            }.addOnFailureListener { exception ->
                setIsLoading(false)
                Log.d("GetJournalsList", "Get failed with ", exception)
            }
    }
}
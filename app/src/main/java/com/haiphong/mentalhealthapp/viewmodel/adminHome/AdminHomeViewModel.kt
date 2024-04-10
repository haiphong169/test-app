package com.haiphong.mentalhealthapp.viewmodel.adminHome

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.haiphong.mentalhealthapp.model.Request
import com.haiphong.mentalhealthapp.model.Specialist
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AdminHomeViewModel : ViewModel() {
    private val _requestsList: MutableStateFlow<List<Request>> = MutableStateFlow(emptyList())
    val requestsList = _requestsList.asStateFlow()

    init {
        getData()
    }

    private fun getData() {
        val db = Firebase.firestore
        db.collection("specialistRequests").get().addOnSuccessListener { documents ->
            if (documents != null) {
                val docList = documents.documents
                val list = mutableListOf<Request>()
                docList.forEach {
                    list.add(
                        Request(
                            requestId = it.id,
                            specialist = Specialist(
                                name = it.data!!["name"].toString(),
                                credentials = it.data!!["credentials"] as List<String>,
                                workplace = it.data!!["workplace"].toString(),
                                pricePerSession = it.data!!["pricePerSession"].toString()
                            )
                        )
                    )
                }
                _requestsList.update {
                    list
                }
            }
        }.addOnFailureListener { exception -> Log.d("Get Requests List", "failed with", exception) }
    }

}
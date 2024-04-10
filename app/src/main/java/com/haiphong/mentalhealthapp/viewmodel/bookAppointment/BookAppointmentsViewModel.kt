package com.haiphong.mentalhealthapp.viewmodel.bookAppointment

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.haiphong.mentalhealthapp.model.Specialist
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class BookAppointmentsViewModel : ViewModel() {
    private val _specialistsList = MutableStateFlow<List<Specialist>>(emptyList())
    val specialistsList = _specialistsList.asStateFlow()

    init {
        getData()
    }

    private fun getData() {
        val db = Firebase.firestore
        db.collection("specialists").get().addOnSuccessListener { documents ->
            if (documents == null) {
                Log.d("Get Specialists", "There are no specialists")
            } else {
                val docList = documents.documents
                val list = mutableListOf<Specialist>()
                docList.forEach {
                    list.add(
                        Specialist(
                            specialistId = it.id,
                            name = it.data!!["name"].toString(),
                            bio = it.data!!["bio"].toString()
                        )
                    )
                }
                _specialistsList.update {
                    list
                }
            }
        }
    }
}
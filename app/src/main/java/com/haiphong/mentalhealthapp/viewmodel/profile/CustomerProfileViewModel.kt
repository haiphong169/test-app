package com.haiphong.mentalhealthapp.viewmodel.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.haiphong.mentalhealthapp.UserAuthentication
import com.haiphong.mentalhealthapp.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CustomerProfileViewModel : ViewModel() {
    private val _profileState = MutableStateFlow(User())
    val profileState = _profileState.asStateFlow()

    init {
        getData()
    }


    private fun getData() {
        val db = Firebase.firestore
        db.collection("customers").document(UserAuthentication.getUid()).get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val data = document.data
                    _profileState.update { currentState ->
                        currentState.copy(
                            name = data?.get("name").toString(),
                            gender = data?.get("gender").toString(),
                            date = data?.get("date").toString(),
                            month = data?.get("month").toString(),
                            year = data?.get("year").toString(),
                            bio = data?.get("bio").toString(),
                            avatarPath = data?.get("avatarPath").toString(),
                        )
                    }
                } else {
                    Log.d("Profile", "No such document")
                }
            }.addOnFailureListener { exception ->
            Log.d("Profile", "get failed with", exception)
        }
    }
}
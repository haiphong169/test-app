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

class CustomerEditProfileViewModel : ViewModel() {
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
                    Log.d("GetProfileInfo", "$data")
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

    fun updateUser(onSave: () -> Unit) {
        val db = Firebase.firestore
        db.collection("customers").document(UserAuthentication.getUid()).set(
            User(
                name = profileState.value.name,
                gender = profileState.value.gender,
                date = profileState.value.date,
                month = profileState.value.month,
                year = profileState.value.year,
                bio = profileState.value.bio,
                avatarPath = profileState.value.avatarPath
            ).toHashMap()
        ).addOnSuccessListener {
            Log.d("UpdateProfile", "DocumentSnapshot updated")
            onSave()
        }.addOnFailureListener { e -> Log.w("FillInfoUser", "Error adding document", e) }
    }

    fun onNameChange(newString: String) {
        _profileState.update { currentState ->
            currentState.copy(
                name = newString
            )
        }
    }

    fun onGenderChange(newString: String) {
        _profileState.update { currentState ->
            currentState.copy(
                gender = newString
            )
        }
    }

    fun onDateChange(newString: String) {
        _profileState.update { currentState ->
            currentState.copy(
                date = newString
            )
        }
    }

    fun onMonthChange(newString: String) {
        _profileState.update { currentState ->
            currentState.copy(
                month = newString
            )
        }
    }

    fun onYearChange(newString: String) {
        _profileState.update { currentState ->
            currentState.copy(
                year = newString
            )
        }
    }

    fun onBioChange(newString: String) {
        _profileState.update { currentState ->
            currentState.copy(
                bio = newString
            )
        }
    }

    fun onAvatarChange(newString: String) {
        _profileState.update { currentState ->
            currentState.copy(
                avatarPath = newString
            )
        }
    }

}
package com.haiphong.mentalhealthapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class IntroState(val status: String = "loading", val userType: UserType = UserType.None)

enum class UserType {
    Customer,
    Specialist,
    None,
    Admin,
}

class IntroViewModel : ViewModel() {
    private var _introState = MutableStateFlow(IntroState())
    val introState = _introState.asStateFlow()

    init {
        if (!UserAuthentication.isSignedIn()) {
            _introState.update {
                it.copy(
                    status = "done"
                )
            }
        } else {
            getUserType()
        }

    }

    private fun getUserType() {
        val db = Firebase.firestore
        val uid = UserAuthentication.getUid()
        db.collection("specialists").document(uid).get()
            .addOnSuccessListener { document ->
                if (document.data != null) {
                    UserAuthentication.userType = "specialist"
                    _introState.update {
                        it.copy(
                            userType = UserType.Specialist,
                            status = "done"
                        )
                    }
                } else {
                    UserAuthentication.userType = "customer"
                    _introState.update {
                        it.copy(
                            userType = UserType.Customer,
                            status = "done"
                        )
                    }
                }
            }
    }
}
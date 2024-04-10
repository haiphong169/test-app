package com.haiphong.mentalhealthapp

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

object UserAuthentication {
    private val auth: FirebaseAuth = Firebase.auth
    var specialistEmail = ""
    var specialistPassword = ""
    var userType = "customer"

    //todo: need to update userType everytime the app reloads
    init {

    }



    fun isSignedIn(): Boolean {
        return auth.currentUser != null
    }

    fun getUid(): String {
        return auth.currentUser?.uid ?: "null"
    }


    fun signUp(email: String, password: String, onSignUp: () -> Unit, onError: (String) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task: Task<AuthResult> ->
                if (task.isSuccessful) {
                    userType = "customer"
                    onSignUp()
                } else {
                    onError(task.exception?.message!!)
                }
            }
    }

    fun signIn(
        email: String,
        password: String,
        onSignIn: (String) -> Unit,
        onError: (String) -> Unit
    ) {
        val db = Firebase.firestore
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task: Task<AuthResult> ->
                if (task.isSuccessful) {
                    val uid = task.result.user!!.uid
                    db.collection("specialists").document(uid).get()
                        .addOnSuccessListener { document ->
                            if (document.data != null) {
                                userType = "specialist"
                                onSignIn("specialists")
                            } else {
                                userType = "customer"
                                onSignIn("customer")
                            }
                        }
                } else {
                    onError(task.exception?.message!!)
                }
            }
    }


    fun signOut(onSignOut: () -> Unit) {
        auth.signOut()
        userType = ""
        onSignOut()
    }

}
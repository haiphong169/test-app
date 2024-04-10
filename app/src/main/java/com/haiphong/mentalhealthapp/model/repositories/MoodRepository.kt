package com.haiphong.mentalhealthapp.model.repositories

import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.haiphong.mentalhealthapp.UserAuthentication
import com.haiphong.mentalhealthapp.model.Mood
import kotlinx.coroutines.tasks.await

interface MoodRepository {
    fun addMood(value: Int)
    suspend fun getMoods(): List<Mood>
}

class MoodRepositoryImpl : MoodRepository {
    override fun addMood(value: Int) {
        Firebase.firestore.collection("moods").add(Mood(value = value).toHashMap())
    }

    override suspend fun getMoods(): List<Mood> {
        val moods = Firebase.firestore.collection("moods")
            .whereEqualTo("customerId", UserAuthentication.getUid()).orderBy("epochSecond", Query.Direction.DESCENDING).get()
            .await()
        val result = mutableListOf<Mood>()
        for (doc in moods) {
            val data = doc.data
            val mood = Mood(
                value = (data["value"] as Long).toInt(),
                epochSecond = data["epochSecond"] as Long
            )
            result.add(mood)
        }
        return result
    }

}
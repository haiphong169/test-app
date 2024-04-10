package com.haiphong.mentalhealthapp.model

data class User(
    val customerId: String = "",
    val name: String = "",
    val gender: String = "",
    val date: String = "",
    val month: String = "",
    val year: String = "",
    val bio: String = "",
    val avatarPath: String = ""
) {
    fun toHashMap(): HashMap<String, String> {
        return hashMapOf(
            "name" to name,
            "gender" to gender,
            "date" to date,
            "month" to month,
            "year" to year,
            "bio" to bio,
            "avatarPath" to avatarPath,
        )
    }
}
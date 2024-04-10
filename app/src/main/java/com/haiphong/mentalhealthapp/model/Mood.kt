package com.haiphong.mentalhealthapp.model

import com.haiphong.mentalhealthapp.R
import com.haiphong.mentalhealthapp.UserAuthentication
import java.time.LocalDateTime
import java.time.ZoneId

data class Mood(
    val value: Int,
    val customerId: String = UserAuthentication.getUid(),
    val epochSecond: Long = LocalDateTime.now().atZone(ZoneId.of("Asia/Ho_Chi_Minh"))
        .toEpochSecond()
) {
    fun toHashMap(): HashMap<String, Any> {
        return hashMapOf(
            "value" to value,
            "customerId" to customerId,
            "epochSecond" to epochSecond
        )
    }
}

fun valueToIcon(value: Int): Int {
    return when(value) {
        1 -> R.drawable.baseline_mood1_24
        2 -> R.drawable.baseline_mood2_24
        3 -> R.drawable.baseline_mood3_24
        4 -> R.drawable.baseline_mood4_24
        5 -> R.drawable.baseline_mood5_24
        else -> 0
    }
}
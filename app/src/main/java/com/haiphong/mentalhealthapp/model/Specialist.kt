package com.haiphong.mentalhealthapp.model

data class Specialist(
    val specialistId: String = "",
    val name: String = "",
    val gender: String = "",
    val date: String = "",
    val month: String = "",
    val year: String = "",
    val bio: String = "",
    val avatarPath: String = "",
    val credentials: List<String> = emptyList(),
    val workplace: String = "Freelance",
    val pricePerSession: String = "",
    val availableTimeSlots: Array<Array<Int>> = Array(7) { Array(12) { 0 } },
) {
    fun toHashMap(): HashMap<String, Any> {
        return hashMapOf(
            "name" to name,
            "gender" to gender,
            "date" to date,
            "month" to month,
            "year" to year,
            "bio" to bio,
            "avatarPath" to avatarPath,
            "credentials" to credentials,
            "workplace" to workplace,
            "pricePerSession" to pricePerSession,
            "availableTimeSlots" to timeSlotsConvert()
        )
    }

    private fun timeSlotsConvert(): List<String> {

        val result: MutableList<String> = mutableListOf()
        for(i in 0..6) {
            for(j in 0..11) {
                if(availableTimeSlots[i][j] == 1) {
                    result += "${"%02d".format(j)}$i"
                }
            }
        }
        return result.toList()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Specialist

        if (specialistId != other.specialistId) return false
        if (name != other.name) return false
        if (gender != other.gender) return false
        if (date != other.date) return false
        if (month != other.month) return false
        if (year != other.year) return false
        if (bio != other.bio) return false
        if (avatarPath != other.avatarPath) return false
        if (credentials != other.credentials) return false
        if (workplace != other.workplace) return false
        if (pricePerSession != other.pricePerSession) return false
        if (!availableTimeSlots.contentDeepEquals(other.availableTimeSlots)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = specialistId.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + gender.hashCode()
        result = 31 * result + date.hashCode()
        result = 31 * result + month.hashCode()
        result = 31 * result + year.hashCode()
        result = 31 * result + bio.hashCode()
        result = 31 * result + avatarPath.hashCode()
        result = 31 * result + credentials.hashCode()
        result = 31 * result + workplace.hashCode()
        result = 31 * result + pricePerSession.hashCode()
        result = 31 * result + availableTimeSlots.contentDeepHashCode()
        return result
    }


}

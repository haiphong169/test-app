package com.haiphong.mentalhealthapp.viewmodel.specialistProfile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haiphong.mentalhealthapp.UserAuthentication
import com.haiphong.mentalhealthapp.model.Specialist
import com.haiphong.mentalhealthapp.model.repositories.SpecialistRepository
import com.haiphong.mentalhealthapp.model.repositories.SpecialistRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SpecialistProfileViewModel : ViewModel() {
    private val _specialist = MutableStateFlow(Specialist())
    val specialist = _specialist.asStateFlow()

    private val specialistRepository: SpecialistRepository = SpecialistRepositoryImpl()

    init {
        viewModelScope.launch {
            getData()
        }
    }

    private suspend fun getData() {
        _specialist.update {
            specialistRepository.getSpecialist(UserAuthentication.getUid()) ?: Specialist()
        }
    }


    /*
    private fun convertTimeSlots(data: List<String>): Array<Array<Int>> {
        val result = Array(7) { Array(12) { 0 } }
        data.forEach {
            val session = it.substring(0, 2).toInt()
            val day = it[2].digitToInt()
            result[day][session] = 1
        }

        return result
    }
     */

    /*
    private fun getData() {
        val db = Firebase.firestore
        db.collection("specialists").document(UserAuthentication.getUid()).get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val data = document.data
                    val newSpecialist = Specialist(
                        name = data!!["name"].toString(),
                        gender = data["gender"].toString(),
                        date = data["date"].toString(),
                        month = data["month"].toString(),
                        year = data["year"].toString(),
                        bio = data["bio"].toString(),
                        avatarPath = data["avatarPath"].toString(),
                        workplace = data["workplace"].toString(),
                        pricePerSession = data["pricePerSession"].toString(),
                        credentials = data["credentials"] as List<String>,
                        availableTimeSlots = convertTimeSlots(data["availableTimeSlots"] as List<String>)
                    )
                    _specialist.update {
                        newSpecialist
                    }

                }
            }.addOnFailureListener { exception ->
                Log.d("Get Specialist Profile", "failed with", exception)
            }
    }
     */
}
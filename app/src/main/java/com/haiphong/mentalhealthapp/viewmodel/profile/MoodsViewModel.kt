package com.haiphong.mentalhealthapp.viewmodel.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haiphong.mentalhealthapp.model.Mood
import com.haiphong.mentalhealthapp.model.repositories.MoodRepository
import com.haiphong.mentalhealthapp.model.repositories.MoodRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MoodsViewModel : ViewModel() {
    private val _moodsList = MutableStateFlow(listOf<Mood>())
    val moodsList = _moodsList.asStateFlow()

    private val moodRepository: MoodRepository = MoodRepositoryImpl()

    init {
        viewModelScope.launch {
            getData()
        }
    }

    private suspend fun getData() {
        _moodsList.update {
            moodRepository.getMoods()
        }
    }
}
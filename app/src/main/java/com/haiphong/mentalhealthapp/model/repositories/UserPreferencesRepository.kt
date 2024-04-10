package com.haiphong.mentalhealthapp.model.repositories

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.longPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import java.time.LocalDateTime
import java.time.ZoneId


class UserPreferencesRepository(private val dataStore: DataStore<Preferences>) {

    val currentTime: Flow<Long> = dataStore.data.catch {
        if (it is IOException) {
            Log.e(TAG, "Error reading preferences.", it)
            emit(emptyPreferences())
        } else {
            throw it
        }
    }.map { preferences ->
        preferences[LAST_MOOD_CHECK] ?: (LocalDateTime.now().atZone(ZoneId.of("Asia/Ho_Chi_Minh"))
            .toEpochSecond() + 10)
    }

    suspend fun updateTime() {
        dataStore.edit { preferences ->
            preferences[LAST_MOOD_CHECK] = newTime
        }
    }

    private companion object {
        val LAST_MOOD_CHECK = longPreferencesKey("last_mood_check")
        private const val INTERVAL = 7200
        private val newTime = LocalDateTime.now().atZone(ZoneId.of("Asia/Ho_Chi_Minh"))
            .toEpochSecond() + INTERVAL
        const val TAG = "UserPreferencesRepo"
    }
}
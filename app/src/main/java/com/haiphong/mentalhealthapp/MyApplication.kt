package com.haiphong.mentalhealthapp

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.haiphong.mentalhealthapp.model.repositories.UserPreferencesRepository

private const val STORED_DATA = "stored_data"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = STORED_DATA
)

class MyApplication : Application() {
    lateinit var userPreferencesRepository: UserPreferencesRepository

    override fun onCreate() {
        super.onCreate()
        userPreferencesRepository = UserPreferencesRepository(dataStore)
    }
}
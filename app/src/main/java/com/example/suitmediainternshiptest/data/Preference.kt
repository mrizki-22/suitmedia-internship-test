package com.example.suitmediainternshiptest.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = "user")

class Preference private constructor(private val dataStore: DataStore<Preferences>) {
    private val USERNAME = stringPreferencesKey("username")

    fun getUsername(): Flow<String> {
        return dataStore.data.map { pref ->
            pref[USERNAME] ?: ""
        }
    }

    suspend fun setUsername(username: String) {
        dataStore.edit { pref ->
            pref[USERNAME] = username

        }
    }

    companion object {
        @Volatile
        private var INSTANCE: Preference? = null

        fun getInstance(dataStore: DataStore<Preferences>): Preference {
            return INSTANCE ?: synchronized(this) {
                val instance = Preference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}
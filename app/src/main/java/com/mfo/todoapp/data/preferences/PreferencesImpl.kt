package com.mfo.todoapp.data.preferences

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import javax.inject.Inject

private const val PREFERENCES_NAME = "preferences_name"

private val Context.dataStore by preferencesDataStore(name = PREFERENCES_NAME)

class PreferencesImpl @Inject constructor(private val context: Context): Preferences {
    override suspend fun putTokenValue(token: String) {
        context.dataStore.edit { preferences ->
            preferences[stringPreferencesKey("token")] = token
        }
    }

    override suspend fun getTokenValue(token: String): String? {
        return try {
            val preferencesKey = stringPreferencesKey(token)
            val preferences = context.dataStore.data.first()
            preferences[preferencesKey]
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
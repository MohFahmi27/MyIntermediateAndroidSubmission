package com.mohfahmi.storyapp.core.data.source.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

const val PREFERENCE_NAME = "com.mohfahmi.storyapp.preferences"
val Context.AppDataStore by preferencesDataStore(PREFERENCE_NAME)

class DataStoreDataSource(private val dataStore: DataStore<Preferences>) {
    private object PreferenceKeys {
        val loginStatePreferenceKey =
            booleanPreferencesKey("com.mohfahmi.storyapp.preferences.login_state")
        val tokenPreferenceKey =
            stringPreferencesKey("com.mohfahmi.storyapp.preferences.token_key")
    }

    suspend fun setLoginState(state: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.loginStatePreferenceKey] = state
        }
    }

    fun getLoginState(): Flow<Boolean> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { value ->
            value[PreferenceKeys.loginStatePreferenceKey] ?: false
        }

    suspend fun setTokenKey(token: String) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.tokenPreferenceKey] = token
        }
    }

    suspend fun deleteTokenKey() {
        dataStore.edit { preferences ->
            preferences.remove(PreferenceKeys.tokenPreferenceKey)
        }
    }

    fun getTokenKey(): Flow<String> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { value ->
            value[PreferenceKeys.tokenPreferenceKey] ?: ""
        }
}
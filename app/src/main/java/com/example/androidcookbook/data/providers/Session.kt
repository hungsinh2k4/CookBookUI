package com.example.androidcookbook.data.providers

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

enum class ThemeType {
    Default,
    Light,
    Dark;

    companion object {
        fun fromString(value: String): ThemeType {
            return values().find { it.name == value } ?: Default
        }
    }
}


val Context.dataStore by preferencesDataStore(name = "auth_preferences")

class DataStoreManager(private val context: Context) {

    private val TOKEN_KEY = stringPreferencesKey("auth_token")
    private val IS_LOGGED_IN_KEY = booleanPreferencesKey("is_logged_in")
    private val USERNAME = stringPreferencesKey("username")
    private val PASSWORD = stringPreferencesKey("password")
    private val THEME_KEY = stringPreferencesKey("theme")
    private val NOTIFICATION_KEY = booleanPreferencesKey("notification")

    // Save token
    suspend fun saveToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = token
            preferences[IS_LOGGED_IN_KEY] = true
        }
    }

    suspend fun saveUsername(username: String) {
        context.dataStore.edit {
            preferences ->
            preferences[USERNAME] = username
        }
    }

    suspend fun savePassword(password: String) {
        context.dataStore.edit {
            preferences ->
            preferences[PASSWORD] = password
        }
    }

    suspend fun saveNotification(notice: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[NOTIFICATION_KEY] = notice
        }
    }

    suspend fun saveTheme(theme: ThemeType) {
        context.dataStore.edit { preferences ->
            preferences[THEME_KEY] = theme.name
        }
    }


    // Get token
    val token: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[TOKEN_KEY]
    }

    // Get username
    val username: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[USERNAME]
    }

    // Get password
    val password: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[PASSWORD]
    }


    // Check login state
    val isLoggedIn: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[IS_LOGGED_IN_KEY] ?: false
    }

    val theme: Flow<ThemeType> = context.dataStore.data.map { preferences ->
        val themeString = preferences[THEME_KEY] ?: ThemeType.Default.name
        ThemeType.fromString(themeString)
    }

    val canSendNotification: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[NOTIFICATION_KEY] ?: false
    }


    // Clear login state
    suspend fun clearLoginState() {
        context.dataStore.edit { preferences ->
            preferences.remove(TOKEN_KEY)
            preferences[IS_LOGGED_IN_KEY] = false
            preferences.remove(USERNAME)
            preferences.remove(PASSWORD)
        }
    }


}
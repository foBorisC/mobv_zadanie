package com.example.mobv_zadanie.data.local

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.mobv_zadanie.domain.model.UserProfile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.profileDataStore by preferencesDataStore(
    name = "user_profile"
)

class ProfileSettingsDataSource(context: Context) {

    private val dataStore = context.applicationContext.profileDataStore

    private object Keys {
        val NAME = stringPreferencesKey("profile_name")
        val AVATAR = stringPreferencesKey("profile_avatar")
        val CREATION_COMPLETED = booleanPreferencesKey("creation_completed")
    }

    val profile: Flow<UserProfile?> = dataStore.data.map { preferences ->
        val completed = preferences[Keys.CREATION_COMPLETED] ?: false
        val name = preferences[Keys.NAME]
        val avatarName = preferences[Keys.AVATAR]

        if (completed && !name.isNullOrBlank() && !avatarName.isNullOrBlank()) {
            UserProfile(
                name = name,
                avatarName = avatarName
            )
        } else {
            null
        }
    }

    suspend fun saveProfile(profile: UserProfile) {
        dataStore.edit { preferences ->
            preferences[Keys.NAME] = profile.name
            preferences[Keys.AVATAR] = profile.avatarName
            preferences[Keys.CREATION_COMPLETED] = true
        }
    }
}
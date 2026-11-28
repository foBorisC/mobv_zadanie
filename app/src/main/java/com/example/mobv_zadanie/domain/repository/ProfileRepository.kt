package com.example.mobv_zadanie.domain.repository

import com.example.mobv_zadanie.domain.model.UserProfile
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    val profile: Flow<UserProfile?>
    suspend fun saveProfile(profile: UserProfile)
}

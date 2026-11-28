package com.example.mobv_zadanie.data.repository

import com.example.mobv_zadanie.data.local.ProfileSettingsDataSource
import com.example.mobv_zadanie.domain.model.UserProfile
import com.example.mobv_zadanie.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow

class ProfileRepositoryImpl(private val dataSource: ProfileSettingsDataSource) : ProfileRepository {

    override val profile: Flow<UserProfile?> = dataSource.profile
    override suspend fun saveProfile(profile: UserProfile) {
        dataSource.saveProfile(profile)
    }
}
package com.example.mobv_zadanie.ui.profile

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.mobv_zadanie.domain.repository.ProfileRepository

fun createProfileViewModelFactory(
    repository: ProfileRepository
): ViewModelProvider.Factory {
    return viewModelFactory {
        initializer {
            ProfileViewModel(repository)
        }
    }
}
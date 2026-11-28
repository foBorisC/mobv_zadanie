package com.example.mobv_zadanie.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobv_zadanie.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException
import com.example.mobv_zadanie.domain.model.UserProfile

class ProfileViewModel(private val repository: ProfileRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileUiState())

    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        loadProfile()
    }

    private fun loadProfile() {
        viewModelScope.launch {
            try {
                val savedProfile = repository.profile.first()
                _uiState.update { currentState ->
                    currentState.copy(
                        name = savedProfile?.name ?: currentState.name,
                        avatarName = savedProfile?.avatarName ?: currentState.avatarName,
                        isLoading = false
                    )
                }
            } catch (e: IOException) {
                _uiState.update { currentState ->
                    currentState.copy(
                        isLoading = false,
                        errorMessage = "Profil sa nepodarilo načítať."
                    )
                }
            }
        }
    }

    fun onNameChanged(newName: String) {
        _uiState.update { currentState ->
            currentState.copy(name = newName)
        }
    }

    fun onAvatarSelected(newAvatarName: String) {
        _uiState.update { currentState ->
            currentState.copy(avatarName = newAvatarName)
        }
    }

    fun saveProfile() {
        val currentState = _uiState.value
        if (currentState.isSaving || currentState.isLoading) {
            return
        }
        val cleanedName = currentState.name.trim()

        if (cleanedName.isBlank()) {
            _uiState.update { state -> state.copy(errorMessage = "Meno je prázne") }
            return
        }

        _uiState.update { state ->
            state.copy(
                isSaving = true,
                isSaved = false,
                errorMessage = null
            )
        }

        viewModelScope.launch {
            try {
                val newProfile = UserProfile(
                    name = cleanedName,
                    avatarName = currentState.avatarName
                )
                repository.saveProfile(newProfile)
                _uiState.update { state ->
                    state.copy(
                        name = cleanedName,
                        isSaving = false,
                        isSaved = true,
                        errorMessage = null
                    )
                }
            } catch (e: IOException) {
                _uiState.update { currentState ->
                    currentState.copy(
                        isSaving = false,
                        errorMessage = "Profil sa nepodarilo uložiť."
                    )
                }
            }
        }
    }
}
package com.example.mobv_zadanie.ui.profile

data class ProfileUiState(
    val name: String = "",
    val avatarName: String = "boy_1",
    val isLoading: Boolean = true,
    val isSaving: Boolean = false,
    val isSaved: Boolean = false,
    val errorMessage: String? = null
)
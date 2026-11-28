package com.example.mobv_zadanie.ui.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mobv_zadanie.domain.repository.ProfileRepository

@Composable
fun ProfileRoute(repository: ProfileRepository) {
    val factory = remember(repository) {
        createProfileViewModelFactory(repository)
    }

    val profileViewModel: ProfileViewModel = viewModel(
        factory = factory
    )

    val uiState by profileViewModel.uiState.collectAsStateWithLifecycle()

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            if (uiState.isLoading) {
                Text("Načítavam profil…")
            } else {
                ProfileScreen(
                    uiState = uiState,
                    onNameChanged = profileViewModel::onNameChanged,
                    onAvatarSelected = profileViewModel::onAvatarSelected,
                    onSaveClick = profileViewModel::saveProfile
                )

                if (uiState.isSaving) {
                    Text("Ukladám profil…")
                }

                if (uiState.isSaved) {
                    Text("Profil bol uložený.")
                }
            }
        }
    }
}
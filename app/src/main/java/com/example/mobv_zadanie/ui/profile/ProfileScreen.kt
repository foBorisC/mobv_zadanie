package com.example.mobv_zadanie.ui.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import com.example.mobv_zadanie.R
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp


private data class AvatarOption(
    val name: String,
    val imageRes: Int
)

private val avatarOptions = listOf(
    AvatarOption("boy_1", R.drawable.boy_1),
    AvatarOption("boy_2", R.drawable.boy_2),
    AvatarOption("boy_3", R.drawable.boy_3),
    AvatarOption("boy_4", R.drawable.boy_4),
    AvatarOption("girl_1", R.drawable.girl_1),
    AvatarOption("girl_2", R.drawable.girl_2),
    AvatarOption("girl_3", R.drawable.girl_3),
    AvatarOption("girl_4", R.drawable.girl_4),
)

@Composable
fun ProfileScreen(
    uiState: ProfileUiState,
    onNameChanged: (String) -> Unit,
    onAvatarSelected: (String) -> Unit,
    onSaveClick: () -> Unit
) {
    Column {
        Text("Profil používateľa")
        OutlinedTextField(
            uiState.name,
            onNameChanged,
            enabled = !(uiState.isSaving || uiState.isLoading),
            label = { Text("Zadajte meno") }
        )
        avatarOptions.chunked(4).forEach { avatarRow ->
            Row {
                avatarRow.forEach { avatar ->
                    val isSelected = uiState.avatarName == avatar.name
                    Surface(
                        selected = isSelected,
                        onClick = { onAvatarSelected(avatar.name) },
                        enabled = !(uiState.isLoading || uiState.isSaving),
                        shape = MaterialTheme.shapes.medium,
                        border = BorderStroke(
                            width = if (isSelected) 3.dp else 1.dp,
                            color = if (isSelected) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                MaterialTheme.colorScheme.outlineVariant
                            }
                        )
                    ) {
                        Image(
                            painter = painterResource(avatar.imageRes),
                            contentDescription = "Avatar ${avatar.name}",
                            modifier = Modifier.size(64.dp)
                        )
                    }

                }
            }
        }

        if (uiState.errorMessage != null) {
            Text(uiState.errorMessage)
        }
        Button(onClick = onSaveClick, enabled = !(uiState.isSaving || uiState.isLoading)) {
            Text("Uložiť")
        }

    }
}
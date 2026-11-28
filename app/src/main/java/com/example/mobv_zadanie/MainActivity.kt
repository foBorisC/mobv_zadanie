package com.example.mobv_zadanie

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mobv_zadanie.data.local.ProfileSettingsDataSource
import com.example.mobv_zadanie.data.repository.ProfileRepositoryImpl
import com.example.mobv_zadanie.domain.repository.ProfileRepository
import com.example.mobv_zadanie.ui.profile.ProfileRoute
import com.example.mobv_zadanie.ui.theme.Mobv_zadanieTheme

private enum class Difficulty(val label: String) {
    ANY("Any"),
    EASY("Easy"),
    MEDIUM("Medium"),
    HARD("Hard")
}

private enum class QuestionType(val label: String) {
    MULTIPLE_CHOICE("Multiple Choice (4 možnosti)"),
    TRUE_FALSE("True / False")
}

class MainActivity : ComponentActivity() {
    private val profileRepository: ProfileRepository by lazy {
        val dataSource = ProfileSettingsDataSource(applicationContext)
        ProfileRepositoryImpl(dataSource)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Mobv_zadanieTheme {
                ProfileRoute(repository = profileRepository)
            }
        }
    }
}

@Composable
private fun QuizLobby() {
    var selectedDifficulty by rememberSaveable { mutableStateOf(Difficulty.ANY) }
    var selectedQuestionType by rememberSaveable { mutableStateOf(QuestionType.MULTIPLE_CHOICE) }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Nastavenie kvízu", style = MaterialTheme.typography.headlineSmall)
            Text("Náročnosť", style = MaterialTheme.typography.titleMedium)
            Column(Modifier.selectableGroup()) {
                Difficulty.entries.forEach { difficulty ->
                    LobbyOption(
                        label = difficulty.label,
                        selected = selectedDifficulty == difficulty,
                        onClick = { selectedDifficulty = difficulty }
                    )
                }
            }
            Text("Typ otázok", style = MaterialTheme.typography.titleMedium)
            Column(Modifier.selectableGroup()) {
                QuestionType.entries.forEach { questionType ->
                    LobbyOption(
                        label = questionType.label,
                        selected = selectedQuestionType == questionType,
                        onClick = { selectedQuestionType = questionType }
                    )
                }
            }
        }
    }
}

@Composable
private fun LobbyOption(label: String, selected: Boolean, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 48.dp)
            .selectable(
                selected = selected,
                onClick = onClick,
                role = Role.RadioButton
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        RadioButton(selected = selected, onClick = null)
        Text(label)
    }
}

@Preview(showBackground = true)
@Composable
private fun QuizLobbyPreview() {
    Mobv_zadanieTheme {
        QuizLobby()
    }
}


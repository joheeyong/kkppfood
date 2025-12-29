package com.luxrobo.smartparing.kkppfood.presentation.screen.detail

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.luxrobo.smartparing.kkppfood.presentation.components.ErrorView
import com.luxrobo.smartparing.kkppfood.presentation.components.LoadingView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    mealId: String, // navGraph에서 넘기지만 ViewModel은 SavedStateHandle로도 읽음
    onBack: () -> Unit,
    viewModel: DetailViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Recipe") },
                navigationIcon = { TextButton(onClick = onBack) { Text("Back") } }
            )
        }
    ) { padding ->
        when {
            state.isLoading -> LoadingView(modifier = Modifier.padding(padding))

            state.errorMessage != null -> ErrorView(
                message = state.errorMessage ?: "Unknown error",
                onRetry = viewModel::load,
                modifier = Modifier.padding(padding)
            )

            state.meal != null -> {
                val meal = state.meal!!
                Column(
                    modifier = Modifier
                        .padding(padding)
                        .padding(16.dp)
                        .fillMaxSize()
                ) {
                    AsyncImage(
                        model = meal.thumbnailUrl,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(220.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    Text(meal.name, style = MaterialTheme.typography.headlineSmall)

                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = listOfNotNull(meal.category, meal.area).joinToString(" • "),
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Spacer(modifier = Modifier.height(14.dp))
                    Text("Instructions", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(meal.instructions.orEmpty())
                }
            }

            else -> ErrorView(message = "Unknown state", modifier = Modifier.padding(padding))
        }
    }
}

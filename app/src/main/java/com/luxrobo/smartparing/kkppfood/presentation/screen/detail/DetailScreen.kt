package com.luxrobo.smartparing.kkppfood.presentation.screen.detail

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
    val isFavorite by viewModel.isFavorite.collectAsState()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Recipe") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = { viewModel.toggleFavorite() },
                        enabled = state.meal != null && !state.isLoading
                    ) {
                        Icon(
                            imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                            contentDescription = "Favorite"
                        )
                    }
                }
            )
        }
    ) { padding ->
        when {
            state.isLoading -> {
                LoadingView(modifier = Modifier.padding(padding))
            }

            state.errorMessage != null -> {
                ErrorView(
                    message = state.errorMessage ?: "Unknown error",
                    onRetry = viewModel::load,
                    modifier = Modifier.padding(padding)
                )
            }

            state.meal != null -> {
                val meal = state.meal!!

                Column(
                    modifier = Modifier
                        .padding(padding)
                        .verticalScroll(rememberScrollState())
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

                    Text(
                        text = meal.name,
                        style = MaterialTheme.typography.headlineSmall
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = listOfNotNull(meal.category, meal.area).joinToString(" • "),
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Ingredients
                    Text(
                        text = "Ingredients",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    if (meal.ingredients.isEmpty()) {
                        Text(
                            text = "No ingredients.",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    } else {
                        meal.ingredients.forEach { ingredient ->
                            Text(
                                text = "• ${ingredient.name} - ${ingredient.measure}",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Instructions
                    Text(
                        text = "Instructions",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = meal.instructions.orEmpty(),
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // YouTube button
                    meal.youtubeUrl
                        ?.takeIf { it.isNotBlank() }
                        ?.let { url ->
                            Button(
                                onClick = {
                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                                    context.startActivity(intent)
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("Watch on YouTube ▶")
                            }
                        }

                    Spacer(modifier = Modifier.height(24.dp))
                }
            }

            else -> {
                ErrorView(
                    message = "Unknown state",
                    modifier = Modifier.padding(padding)
                )
            }
        }
    }
}

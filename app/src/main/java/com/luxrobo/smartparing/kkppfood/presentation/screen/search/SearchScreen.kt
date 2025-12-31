package com.luxrobo.smartparing.kkppfood.presentation.screen.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.luxrobo.smartparing.kkppfood.domain.model.Meal
import com.luxrobo.smartparing.kkppfood.presentation.components.ErrorView
import com.luxrobo.smartparing.kkppfood.presentation.components.LoadingView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    onOpenCategory: () -> Unit,
    onOpenFavorite: () -> Unit,
    onOpenDetail: (String) -> Unit,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("KKPP Food") },
                actions = {
                    TextButton(onClick = onOpenCategory) { Text("Categories") }
                    IconButton(onClick = onOpenFavorite) {
                        Icon(
                            imageVector = Icons.Filled.Favorite,
                            contentDescription = "Favorite"
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(
                    modifier = Modifier.weight(1f),
                    value = state.query,
                    onValueChange = viewModel::onQueryChange,
                    singleLine = true,
                    label = { Text("Search meal") },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Search
                    )
                )

                Spacer(modifier = Modifier.width(12.dp))

                Button(
                    onClick = { viewModel.search() },
                    enabled = state.query.trim().isNotEmpty() && !state.isLoading
                ) {
                    Text("Search")
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            when {
                state.isLoading -> {
                    LoadingView(modifier = Modifier.fillMaxSize())
                }

                state.errorMessage != null -> {
                    ErrorView(
                        message = state.errorMessage ?: "Unknown error",
                        onRetry = viewModel::search,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                state.query.isNotBlank() && state.meals.isEmpty() -> {
                    Text("No results.")
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(vertical = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(state.meals) { meal ->
                            MealItem(meal = meal, onClick = { onOpenDetail(meal.id) })
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun MealItem(
    meal: Meal,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Row(modifier = Modifier.padding(12.dp)) {
            AsyncImage(
                model = meal.thumbnailUrl,
                contentDescription = null,
                modifier = Modifier.size(72.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = meal.name,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = listOfNotNull(meal.category, meal.area)
                        .joinToString(" • ")
                        .ifBlank { "Unknown" },
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

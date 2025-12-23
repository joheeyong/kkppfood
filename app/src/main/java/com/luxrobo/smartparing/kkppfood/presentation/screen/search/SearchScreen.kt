package com.luxrobo.smartparing.kkppfood.presentation.screen.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.luxrobo.smartparing.kkppfood.domain.model.Meal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    onOpenCategories: () -> Unit,
    onOpenDetail: (String) -> Unit,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("KKPP Food") },
                actions = {
                    TextButton(onClick = onOpenCategories) { Text("Categories") }
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
                        keyboardType = KeyboardType.Text,
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

            if (state.isLoading) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                }
                Spacer(modifier = Modifier.height(12.dp))
            }

            state.errorMessage?.let { msg ->
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier.weight(1f),
                            text = msg,
                            color = MaterialTheme.colorScheme.onErrorContainer
                        )
                        TextButton(onClick = viewModel::clearError) { Text("OK") }
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
            }

            if (!state.isLoading && state.errorMessage == null && state.meals.isEmpty() && state.query.isNotBlank()) {
                Text("No results.")
                Spacer(modifier = Modifier.height(8.dp))
            }

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
        Column(modifier = Modifier.padding(14.dp)) {
            Text(
                text = meal.name,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = listOfNotNull(meal.category, meal.area).joinToString(" • ").ifBlank { "Unknown" },
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

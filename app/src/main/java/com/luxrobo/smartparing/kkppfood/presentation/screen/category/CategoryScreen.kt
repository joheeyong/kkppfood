package com.luxrobo.smartparing.kkppfood.presentation.screen.category

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.luxrobo.smartparing.kkppfood.domain.model.Meal
import com.luxrobo.smartparing.kkppfood.domain.model.MealCategory
import com.luxrobo.smartparing.kkppfood.presentation.components.EmptyView
import com.luxrobo.smartparing.kkppfood.presentation.components.ErrorView
import com.luxrobo.smartparing.kkppfood.presentation.components.LoadingView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryScreen(
    onBack: () -> Unit,
    onOpenDetail: (String) -> Unit,
    viewModel: CategoryViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Categories") },
                navigationIcon = { TextButton(onClick = onBack) { Text("Back") } }
            )
        }
    ) { padding ->

        when {
            state.isLoading && state.categories.isEmpty() -> {
                LoadingView(modifier = Modifier.padding(padding))
            }

            state.errorMessage != null && state.categories.isEmpty() -> {
                ErrorView(
                    message = state.errorMessage ?: "Unknown error",
                    onRetry = viewModel::loadCategories,
                    modifier = Modifier.padding(padding)
                )
            }

            else -> {
                Column(
                    modifier = Modifier
                        .padding(padding)
                        .padding(16.dp)
                        .fillMaxSize()
                ) {
                    CategoryChipsRow(
                        categories = state.categories,
                        selected = state.selectedCategory,
                        onClick = viewModel::selectCategory
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    if (state.isLoading) {
                        LoadingView(modifier = Modifier.fillMaxSize())
                        return@Column
                    }

                    state.errorMessage?.let { msg ->
                        ErrorView(message = msg, onRetry = {
                            state.selectedCategory?.let { viewModel.selectCategory(it) }
                        })
                        return@Column
                    }

                    if (state.meals.isEmpty()) {
                        EmptyView(text = "No meals in this category.")
                        return@Column
                    }

                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        contentPadding = PaddingValues(bottom = 24.dp)
                    ) {
                        items(state.meals) { meal ->
                            MealCard(meal = meal, onClick = { onOpenDetail(meal.id) })
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CategoryChipsRow(
    categories: List<MealCategory>,
    selected: MealCategory?,
    onClick: (MealCategory) -> Unit
) {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        items(categories) { item ->
            val isSelected = selected?.id == item.id
            AssistChip(
                onClick = { onClick(item) },
                label = { Text(item.name) },
                colors = AssistChipDefaults.assistChipColors(
                    containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface
                )
            )
        }
    }
}

@Composable
private fun MealCard(
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
                modifier = Modifier
                    .size(72.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(meal.name, style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = listOfNotNull(meal.category, meal.area).joinToString(" • ").ifBlank { "Unknown" },
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

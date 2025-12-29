package com.luxrobo.smartparing.kkppfood.presentation.screen.category

import com.luxrobo.smartparing.kkppfood.domain.model.Meal
import com.luxrobo.smartparing.kkppfood.domain.model.MealCategory

data class CategoryUiState(
    val isLoading: Boolean = false,
    val categories: List<MealCategory> = emptyList(),
    val selectedCategory: MealCategory? = null,
    val meals: List<Meal> = emptyList(),
    val errorMessage: String? = null
)

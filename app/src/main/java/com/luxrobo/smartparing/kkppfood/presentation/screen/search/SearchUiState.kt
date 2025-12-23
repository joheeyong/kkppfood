package com.luxrobo.smartparing.kkppfood.presentation.screen.search

import com.luxrobo.smartparing.kkppfood.domain.model.Meal

data class SearchUiState(
    val query: String = "",
    val isLoading: Boolean = false,
    val meals: List<Meal> = emptyList(),
    val errorMessage: String? = null
)

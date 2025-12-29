package com.luxrobo.smartparing.kkppfood.presentation.screen.detail

import com.luxrobo.smartparing.kkppfood.domain.model.Meal

data class DetailUiState(
    val isLoading: Boolean = true,
    val meal: Meal? = null,
    val errorMessage: String? = null
)

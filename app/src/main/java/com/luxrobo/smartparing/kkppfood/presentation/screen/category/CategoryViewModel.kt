package com.luxrobo.smartparing.kkppfood.presentation.screen.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luxrobo.smartparing.kkppfood.core.util.Result
import com.luxrobo.smartparing.kkppfood.domain.model.MealCategory
import com.luxrobo.smartparing.kkppfood.domain.usecase.GetCategoriesUseCase
import com.luxrobo.smartparing.kkppfood.domain.usecase.GetMealsByCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getMealsByCategoryUseCase: GetMealsByCategoryUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(CategoryUiState(isLoading = true))
    val uiState: StateFlow<CategoryUiState> = _uiState

    init {
        loadCategories()
    }

    fun loadCategories() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            when (val result = getCategoriesUseCase()) {
                is Result.Success -> {
                    val categories = result.data
                    val first = categories.firstOrNull()
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            categories = categories,
                            selectedCategory = first,
                            errorMessage = null
                        )
                    }
                    first?.let { loadMealsByCategory(it.name) }
                }
                is Result.Error -> {
                    _uiState.update {
                        it.copy(isLoading = false, errorMessage = result.message ?: "Failed to load categories")
                    }
                }
                Result.Loading -> Unit
            }
        }
    }

    fun selectCategory(category: MealCategory) {
        _uiState.update { it.copy(selectedCategory = category, meals = emptyList(), errorMessage = null) }
        loadMealsByCategory(category.name)
    }

    private fun loadMealsByCategory(categoryName: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            when (val result = getMealsByCategoryUseCase(categoryName)) {
                is Result.Success -> {
                    _uiState.update { it.copy(isLoading = false, meals = result.data, errorMessage = null) }
                }
                is Result.Error -> {
                    _uiState.update { it.copy(isLoading = false, errorMessage = result.message ?: "Failed to load meals") }
                }
                Result.Loading -> Unit
            }
        }
    }
}

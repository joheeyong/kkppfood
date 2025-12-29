package com.luxrobo.smartparing.kkppfood.presentation.screen.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luxrobo.smartparing.kkppfood.core.util.Result
import com.luxrobo.smartparing.kkppfood.domain.usecase.GetMealDetailUseCase
import com.luxrobo.smartparing.kkppfood.presentation.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getMealDetailUseCase: GetMealDetailUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val mealId: String =
        savedStateHandle.get<String>(Screen.Detail.ARG_MEAL_ID).orEmpty()


    private val _uiState = MutableStateFlow(DetailUiState())
    val uiState: StateFlow<DetailUiState> = _uiState

    init {
        load()
    }

    fun load() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            when (val result = getMealDetailUseCase(mealId)) {
                is Result.Success -> _uiState.update { it.copy(isLoading = false, meal = result.data) }
                is Result.Error -> _uiState.update { it.copy(isLoading = false, errorMessage = result.message ?: "Failed to load detail") }
                Result.Loading -> Unit
            }
        }
    }
}

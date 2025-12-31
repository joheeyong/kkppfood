// app/src/main/java/com/luxrobo/smartparing/kkppfood/presentation/screen/detail/DetailViewModel.kt
package com.luxrobo.smartparing.kkppfood.presentation.screen.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luxrobo.smartparing.kkppfood.core.util.Result
import com.luxrobo.smartparing.kkppfood.domain.usecase.GetMealDetailUseCase
import com.luxrobo.smartparing.kkppfood.domain.usecase.IsFavoriteUseCase
import com.luxrobo.smartparing.kkppfood.domain.usecase.ToggleFavoriteUseCase
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
    private val isFavoriteUseCase: IsFavoriteUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val mealId: String =
        savedStateHandle.get<String>(Screen.Detail.ARG_MEAL_ID).orEmpty()

    private val _uiState = MutableStateFlow(DetailUiState())
    val uiState: StateFlow<DetailUiState> = _uiState

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite

    init {
        load()
    }

    fun load() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            when (val result = getMealDetailUseCase(mealId)) {
                is Result.Success -> {
                    _uiState.update { it.copy(isLoading = false, meal = result.data, errorMessage = null) }
                    // 상세가 정상 로드된 뒤 즐겨찾기 여부 조회
                    _isFavorite.value = isFavoriteUseCase(mealId)
                }

                is Result.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            meal = null,
                            errorMessage = result.message ?: "Failed to load detail"
                        )
                    }
                }

                Result.Loading -> {
                    _uiState.update { it.copy(isLoading = true) }
                }
            }
        }
    }

    fun toggleFavorite() {
        val meal = _uiState.value.meal ?: return
        viewModelScope.launch {
            val current = _isFavorite.value
            toggleFavoriteUseCase(meal, current)
            _isFavorite.value = !current
        }
    }
}

package com.luxrobo.smartparing.kkppfood.presentation.screen.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luxrobo.smartparing.kkppfood.core.util.Result
import com.luxrobo.smartparing.kkppfood.domain.usecase.SearchMealsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchMealsUseCase: SearchMealsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState

    fun onQueryChange(query: String) {
        _uiState.update { it.copy(query = query) }
    }

    fun search() {
        val q = uiState.value.query.trim()
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            when (val result = searchMealsUseCase(q)) {
                is Result.Success -> {
                    _uiState.update {
                        it.copy(isLoading = false, meals = result.data, errorMessage = null)
                    }
                }
                is Result.Error -> {
                    _uiState.update {
                        it.copy(isLoading = false, meals = emptyList(), errorMessage = result.message ?: "Unknown error")
                    }
                }
                Result.Loading -> {
                    // 지금 UseCase에서 Loading을 안 쓰지만, 형태 맞추기용
                    _uiState.update { it.copy(isLoading = true) }
                }
            }
        }
    }

    fun clearError() {
        _uiState.update { it.copy(errorMessage = null) }
    }
}

package com.luxrobo.smartparing.kkppfood.presentation.screen.favorite

import androidx.lifecycle.ViewModel
import com.luxrobo.smartparing.kkppfood.domain.usecase.ObserveFavoritesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    observeFavoritesUseCase: ObserveFavoritesUseCase
) : ViewModel() {
    val favorites = observeFavoritesUseCase()
}

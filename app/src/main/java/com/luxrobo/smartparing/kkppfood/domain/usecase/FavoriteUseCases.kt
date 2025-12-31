package com.luxrobo.smartparing.kkppfood.domain.usecase

import com.luxrobo.smartparing.kkppfood.domain.model.Meal
import com.luxrobo.smartparing.kkppfood.domain.repository.FavoriteRepository
import javax.inject.Inject

class ObserveFavoritesUseCase @Inject constructor(
    private val repo: FavoriteRepository
) {
    operator fun invoke() = repo.observeFavorites()
}

class ToggleFavoriteUseCase @Inject constructor(
    private val repo: FavoriteRepository
) {
    suspend operator fun invoke(meal: Meal, isFavorite: Boolean) {
        if (isFavorite) repo.removeFavorite(meal)
        else repo.addFavorite(meal)
    }
}

class IsFavoriteUseCase @Inject constructor(
    private val repo: FavoriteRepository
) {
    suspend operator fun invoke(mealId: String) = repo.isFavorite(mealId)
}

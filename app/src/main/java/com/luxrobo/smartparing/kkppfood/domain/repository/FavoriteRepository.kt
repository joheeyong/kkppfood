package com.luxrobo.smartparing.kkppfood.domain.repository

import kotlinx.coroutines.flow.Flow
import com.luxrobo.smartparing.kkppfood.domain.model.Meal

interface FavoriteRepository {
    fun observeFavorites(): Flow<List<Meal>>
    suspend fun isFavorite(mealId: String): Boolean
    suspend fun addFavorite(meal: Meal)
    suspend fun removeFavorite(meal: Meal)
}

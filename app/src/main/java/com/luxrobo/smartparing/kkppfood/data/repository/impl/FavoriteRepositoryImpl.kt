package com.luxrobo.smartparing.kkppfood.data.repository.impl

import com.luxrobo.smartparing.kkppfood.data.local.dao.FavoriteMealDao
import com.luxrobo.smartparing.kkppfood.data.local.entity.FavoriteMealEntity
import com.luxrobo.smartparing.kkppfood.domain.model.Meal
import com.luxrobo.smartparing.kkppfood.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(
    private val dao: FavoriteMealDao
) : FavoriteRepository {

    override fun observeFavorites(): Flow<List<Meal>> =
        dao.observeFavorites().map { list ->
            list.map {
                Meal(
                    id = it.mealId,
                    name = it.name,
                    thumbnailUrl = it.thumbnailUrl,
                    category = it.category,
                    area = it.area,
                    instructions = null,
                    tags = emptyList()
                )
            }
        }

    override suspend fun isFavorite(mealId: String): Boolean =
        dao.getFavorite(mealId) != null

    override suspend fun addFavorite(meal: Meal) {
        dao.insert(
            FavoriteMealEntity(
                mealId = meal.id,
                name = meal.name,
                thumbnailUrl = meal.thumbnailUrl,
                category = meal.category,
                area = meal.area
            )
        )
    }

    override suspend fun removeFavorite(meal: Meal) {
        dao.getFavorite(meal.id)?.let { dao.delete(it) }
    }
}

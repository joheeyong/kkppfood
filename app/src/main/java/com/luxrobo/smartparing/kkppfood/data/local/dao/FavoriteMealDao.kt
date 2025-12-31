package com.luxrobo.smartparing.kkppfood.data.local.dao

import androidx.room.*
import com.luxrobo.smartparing.kkppfood.data.local.entity.FavoriteMealEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteMealDao {

    @Query("SELECT * FROM favorite_meals")
    fun observeFavorites(): Flow<List<FavoriteMealEntity>>

    @Query("SELECT * FROM favorite_meals WHERE mealId = :mealId LIMIT 1")
    suspend fun getFavorite(mealId: String): FavoriteMealEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: FavoriteMealEntity)

    @Delete
    suspend fun delete(entity: FavoriteMealEntity)
}

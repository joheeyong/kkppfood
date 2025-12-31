package com.luxrobo.smartparing.kkppfood.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_meals")
data class FavoriteMealEntity(
    @PrimaryKey
    val mealId: String,
    val name: String,
    val thumbnailUrl: String?,
    val category: String?,
    val area: String?
)

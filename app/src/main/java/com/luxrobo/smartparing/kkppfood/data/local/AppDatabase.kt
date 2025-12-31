package com.luxrobo.smartparing.kkppfood.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.luxrobo.smartparing.kkppfood.data.local.dao.FavoriteMealDao
import com.luxrobo.smartparing.kkppfood.data.local.entity.FavoriteMealEntity

@Database(
    entities = [FavoriteMealEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteMealDao(): FavoriteMealDao
}

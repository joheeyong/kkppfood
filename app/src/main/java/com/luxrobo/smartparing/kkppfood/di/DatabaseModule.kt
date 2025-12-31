package com.luxrobo.smartparing.kkppfood.di

import android.content.Context
import androidx.room.Room
import com.luxrobo.smartparing.kkppfood.data.local.AppDatabase
import com.luxrobo.smartparing.kkppfood.data.local.dao.FavoriteMealDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "kkppfood.db"
        ).build()

    @Provides
    fun provideFavoriteMealDao(db: AppDatabase): FavoriteMealDao =
        db.favoriteMealDao()
}

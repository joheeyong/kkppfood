package com.luxrobo.smartparing.kkppfood.di

import com.luxrobo.smartparing.kkppfood.data.repository.impl.FavoriteRepositoryImpl
import com.luxrobo.smartparing.kkppfood.data.repository.impl.MealRepositoryImpl
import com.luxrobo.smartparing.kkppfood.domain.repository.FavoriteRepository
import com.luxrobo.smartparing.kkppfood.domain.repository.MealRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindMealRepository(
        impl: MealRepositoryImpl
    ): MealRepository

    @Binds
    @Singleton
    abstract fun bindFavoriteRepository(
        impl: FavoriteRepositoryImpl
    ): FavoriteRepository

}

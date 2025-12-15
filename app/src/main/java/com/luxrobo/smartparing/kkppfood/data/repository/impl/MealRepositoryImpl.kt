package com.luxrobo.smartparing.kkppfood.data.repository.impl

import com.luxrobo.smartparing.kkppfood.core.util.Result
import com.luxrobo.smartparing.kkppfood.data.mapper.toDomain
import com.luxrobo.smartparing.kkppfood.data.remote.MealApiService
import com.luxrobo.smartparing.kkppfood.domain.model.Meal
import com.luxrobo.smartparing.kkppfood.domain.model.MealCategory
import com.luxrobo.smartparing.kkppfood.domain.repository.MealRepository
import javax.inject.Inject

class MealRepositoryImpl @Inject constructor(
    private val api: MealApiService
) : MealRepository {

    override suspend fun searchMeals(query: String): Result<List<Meal>> = runCatching {
        val res = api.searchMeals(query)
        val meals = res.meals.orEmpty().map { it.toDomain() }
        Result.Success(meals)
    }.getOrElse { e ->
        Result.Error(e)
    }

    override suspend fun getCategories(): Result<List<MealCategory>> = runCatching {
        val res = api.getCategories()
        val categories = res.categories.orEmpty().map { it.toDomain() }
        Result.Success(categories)
    }.getOrElse { e ->
        Result.Error(e)
    }

    override suspend fun getMealsByCategory(category: String): Result<List<Meal>> = runCatching {
        val res = api.getMealsByCategory(category)
        val meals = res.meals.orEmpty().map { it.toDomain() }
        Result.Success(meals)
    }.getOrElse { e ->
        Result.Error(e)
    }

    override suspend fun getMealDetail(id: String): Result<Meal> = runCatching {
        val res = api.getMealDetail(id)
        val mealDto = res.meals?.firstOrNull()
            ?: return@runCatching Result.Error(
                message = "Meal detail not found (id=$id)"
            )
        Result.Success(mealDto.toDomain())
    }.getOrElse { e ->
        Result.Error(e)
    }
}

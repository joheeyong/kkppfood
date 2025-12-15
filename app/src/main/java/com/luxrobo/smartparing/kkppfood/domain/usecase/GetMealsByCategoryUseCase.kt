package com.luxrobo.smartparing.kkppfood.domain.usecase

import com.luxrobo.smartparing.kkppfood.core.util.Result
import com.luxrobo.smartparing.kkppfood.domain.model.Meal
import com.luxrobo.smartparing.kkppfood.domain.repository.MealRepository
import javax.inject.Inject

class GetMealsByCategoryUseCase @Inject constructor(
    private val repository: MealRepository
) {
    suspend operator fun invoke(category: String): Result<List<Meal>> {
        val c = category.trim()
        if (c.isBlank()) return Result.Success(emptyList())
        return repository.getMealsByCategory(c)
    }
}

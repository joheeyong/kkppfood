package com.luxrobo.smartparing.kkppfood.domain.usecase

import com.luxrobo.smartparing.kkppfood.core.util.Result
import com.luxrobo.smartparing.kkppfood.domain.model.Meal
import com.luxrobo.smartparing.kkppfood.domain.repository.MealRepository
import javax.inject.Inject

class GetMealDetailUseCase @Inject constructor(
    private val repository: MealRepository
) {
    suspend operator fun invoke(id: String): Result<Meal> {
        val mealId = id.trim()
        if (mealId.isBlank()) return Result.Error(message = "Invalid meal id")
        return repository.getMealDetail(mealId)
    }
}

package com.luxrobo.smartparing.kkppfood.domain.usecase

import com.luxrobo.smartparing.kkppfood.core.util.Result
import com.luxrobo.smartparing.kkppfood.domain.model.Meal
import com.luxrobo.smartparing.kkppfood.domain.repository.MealRepository
import javax.inject.Inject

class SearchMealsUseCase @Inject constructor(
    private val repository: MealRepository
) {
    suspend operator fun invoke(query: String): Result<List<Meal>> {
        val q = query.trim()
        if (q.isBlank()) return Result.Success(emptyList())
        return repository.searchMeals(q)
    }
}

package com.luxrobo.smartparing.kkppfood.domain.usecase

import com.luxrobo.smartparing.kkppfood.core.util.Result
import com.luxrobo.smartparing.kkppfood.domain.model.MealCategory
import com.luxrobo.smartparing.kkppfood.domain.repository.MealRepository
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val repository: MealRepository
) {
    suspend operator fun invoke(): Result<List<MealCategory>> {
        return repository.getCategories()
    }
}

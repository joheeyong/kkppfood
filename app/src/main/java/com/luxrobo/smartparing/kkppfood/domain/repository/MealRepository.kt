package com.luxrobo.smartparing.kkppfood.domain.repository

import com.luxrobo.smartparing.kkppfood.core.util.Result
import com.luxrobo.smartparing.kkppfood.domain.model.Meal
import com.luxrobo.smartparing.kkppfood.domain.model.MealCategory

/**
 * TheMealDB 관련 도메인 레벨 Repository 인터페이스.
 * 실제 구현은 data 레이어에서 담당.
 */
interface MealRepository {

    suspend fun searchMeals(query: String): Result<List<Meal>>

    suspend fun getCategories(): Result<List<MealCategory>>

    suspend fun getMealsByCategory(category: String): Result<List<Meal>>

    suspend fun getMealDetail(id: String): Result<Meal>
}

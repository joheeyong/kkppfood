package com.luxrobo.smartparing.kkppfood.data.remote

import com.luxrobo.smartparing.kkppfood.data.remote.dto.CategoryResponse
import com.luxrobo.smartparing.kkppfood.data.remote.dto.MealResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * TheMealDB REST API 인터페이스.
 * baseUrl: https://www.themealdb.com/api/json/v1/1/
 */
interface MealApiService {

    /**
     * 이름으로 레시피 검색
     * 예: search.php?s=Arrabiata
     */
    @GET("search.php")
    suspend fun searchMeals(
        @Query("s") query: String
    ): MealResponse

    /**
     * 카테고리 목록 조회
     * 예: categories.php
     */
    @GET("categories.php")
    suspend fun getCategories(): CategoryResponse

    /**
     * 카테고리별 레시피 목록 조회
     * 예: filter.php?c=Seafood
     */
    @GET("filter.php")
    suspend fun getMealsByCategory(
        @Query("c") category: String
    ): MealResponse

    /**
     * 레시피 상세 조회 (id 기준)
     * 예: lookup.php?i=52772
     */
    @GET("lookup.php")
    suspend fun getMealDetail(
        @Query("i") id: String
    ): MealResponse
}

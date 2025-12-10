package com.luxrobo.smartparing.kkppfood.data.remote.dto

import com.google.gson.annotations.SerializedName

/**
 * search.php, filter.php, lookup.php 등에서 공통으로 사용하는 래퍼
 */
data class MealResponse(
    @SerializedName("meals")
    val meals: List<MealDto>?
)

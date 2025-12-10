package com.luxrobo.smartparing.kkppfood.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CategoryResponse(
    @SerializedName("categories")
    val categories: List<CategoryDto>?
)

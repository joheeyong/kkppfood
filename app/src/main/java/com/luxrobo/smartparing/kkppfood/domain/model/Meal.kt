package com.luxrobo.smartparing.kkppfood.domain.model

data class Meal(
    val id: String,
    val name: String,
    val category: String?,
    val area: String?,
    val thumbnailUrl: String?,
    val instructions: String?,
    val tags: List<String> = emptyList()
)

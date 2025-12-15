package com.luxrobo.smartparing.kkppfood.data.mapper

import com.luxrobo.smartparing.kkppfood.data.remote.dto.CategoryDto
import com.luxrobo.smartparing.kkppfood.domain.model.MealCategory

internal fun CategoryDto.toDomain(): MealCategory {
    return MealCategory(
        id = idCategory.orEmpty(),
        name = strCategory.orEmpty(),
        description = strCategoryDescription,
        thumbnailUrl = strCategoryThumb
    )
}

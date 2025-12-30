package com.luxrobo.smartparing.kkppfood.data.mapper

import com.luxrobo.smartparing.kkppfood.data.remote.dto.MealDto
import com.luxrobo.smartparing.kkppfood.domain.model.Ingredient
import com.luxrobo.smartparing.kkppfood.domain.model.Meal

internal fun MealDto.toDomain(): Meal {
    val tags = strTags
        ?.split(",")
        ?.map { it.trim() }
        ?.filter { it.isNotBlank() }
        ?: emptyList()

    val ingredients = extractIngredients().map {
        Ingredient(
            name = it.first,
            measure = it.second
        )
    }

    return Meal(
        id = idMeal.orEmpty(),
        name = strMeal.orEmpty(),
        category = strCategory,
        area = strArea,
        thumbnailUrl = strMealThumb,
        instructions = strInstructions,
        tags = tags,
        ingredients = ingredients,
        youtubeUrl = strYoutube
    )
}


/**
 * 상세화면에서 사용할 재료/계량 리스트 생성용 헬퍼.
 * (도메인 모델에 지금은 안 넣었지만, 다음 커밋에서 Detail 모델 확장할 때 재사용 가능)
 */
internal fun MealDto.extractIngredients(): List<Pair<String, String>> {
    val ingredients = listOf(
        strIngredient1 to strMeasure1,
        strIngredient2 to strMeasure2,
        strIngredient3 to strMeasure3,
        strIngredient4 to strMeasure4,
        strIngredient5 to strMeasure5,
        strIngredient6 to strMeasure6,
        strIngredient7 to strMeasure7,
        strIngredient8 to strMeasure8,
        strIngredient9 to strMeasure9,
        strIngredient10 to strMeasure10,
        strIngredient11 to strMeasure11,
        strIngredient12 to strMeasure12,
        strIngredient13 to strMeasure13,
        strIngredient14 to strMeasure14,
        strIngredient15 to strMeasure15,
        strIngredient16 to strMeasure16,
        strIngredient17 to strMeasure17,
        strIngredient18 to strMeasure18,
        strIngredient19 to strMeasure19,
        strIngredient20 to strMeasure20
    )

    return ingredients
        .mapNotNull { (ing, mea) ->
            val i = ing?.trim().orEmpty()
            if (i.isBlank()) return@mapNotNull null
            val m = mea?.trim().orEmpty()
            i to m
        }
}

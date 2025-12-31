package com.luxrobo.smartparing.kkppfood.presentation.navigation

sealed class Screen(val route: String) {
    data object Search : Screen("search")
    data object Category : Screen("category")

    data object Favorite : Screen("favorite")
    data object Detail : Screen("detail/{mealId}") {
        fun createRoute(mealId: String) = "detail/$mealId"
        const val ARG_MEAL_ID = "mealId"
    }
}

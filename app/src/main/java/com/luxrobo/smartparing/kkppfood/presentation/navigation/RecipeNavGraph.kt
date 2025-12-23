package com.luxrobo.smartparing.kkppfood.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.NavType
import com.luxrobo.smartparing.kkppfood.presentation.screen.category.CategoryScreen
import com.luxrobo.smartparing.kkppfood.presentation.screen.detail.DetailScreen
import com.luxrobo.smartparing.kkppfood.presentation.screen.search.SearchScreen

@Composable
fun RecipeNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Search.route
    ) {
        composable(Screen.Search.route) {
            SearchScreen(
                onOpenCategories = { navController.navigate(Screen.Category.route) },
                onOpenDetail = { mealId -> navController.navigate(Screen.Detail.createRoute(mealId)) }
            )
        }

        composable(Screen.Category.route) {
            CategoryScreen(
                onBack = { navController.popBackStack() },
                onOpenDetail = { mealId -> navController.navigate(Screen.Detail.createRoute(mealId)) }
            )
        }

        composable(
            route = Screen.Detail.route,
            arguments = listOf(navArgument(Screen.Detail.ARG_MEAL_ID) { type = NavType.StringType })
        ) { entry ->
            val mealId = entry.arguments?.getString(Screen.Detail.ARG_MEAL_ID).orEmpty()
            DetailScreen(
                mealId = mealId,
                onBack = { navController.popBackStack() }
            )
        }
    }
}

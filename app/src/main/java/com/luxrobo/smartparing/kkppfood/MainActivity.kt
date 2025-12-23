package com.luxrobo.smartparing.kkppfood

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.luxrobo.smartparing.kkppfood.presentation.navigation.RecipeNavGraph
import com.luxrobo.smartparing.kkppfood.ui.theme.KkppFoodTheme

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KkppFoodTheme {
                val navController = rememberNavController()
                RecipeNavGraph(navController = navController)
            }
        }
    }
}

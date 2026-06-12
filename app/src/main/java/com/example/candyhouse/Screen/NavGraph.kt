package com.example.candyhouse.Screen

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = "inicio"
    ) {
        composable("inicio") {
            InicioScreen()
        }
        composable("historial") {
            HistorialScreen()
        }
        composable("detail") {
            DetailScreen(
                onClose = { navController.popBackStack() }
            )
        }
    }
}
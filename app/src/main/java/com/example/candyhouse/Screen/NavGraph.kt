package com.example.candyhouse.Screen

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel // 🌟 Asegúrate de tener este import
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController()
) {
    val candyViewModel: CandyViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = "inicio"
    ) {
        composable("inicio") {
            InicioScreen(
                onIrAFiltros = { navController.navigate("filtros") },
                viewModel = candyViewModel
            )
        }

        composable("filtros") {
            FiltrosScreen(
                onIrAInicio = { navController.navigate("inicio") },
                viewModel = candyViewModel
            )
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
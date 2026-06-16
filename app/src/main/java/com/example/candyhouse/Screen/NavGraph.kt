package com.example.candyhouse.Screen

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

// IMPORTS DE TU VIEWMODEL Y PANTALLAS
import com.example.candyhouse.viewmodel.CandyViewModel

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
                viewModel = candyViewModel,
                navController = navController
            )
        }

        composable("filtros") {
            FiltrosScreen(
                onIrAInicio = { navController.navigate("inicio") },
                viewModel = candyViewModel
            )
        }

        composable("historial") {
            HistorialScreen(navController = navController)
        }

        composable("detail") {
            DetailScreen(
                onClose = { navController.popBackStack() }
            )
        }

        composable("agregar") {
            AddProducts(
                navController = navController,
                rutaActual = "agregar"
            )
        }
        composable("notificaciones") {
            NotificacionesScreen(
                navController = navController,
                rutaActual = "notificaciones"
            )
        }
    }
}
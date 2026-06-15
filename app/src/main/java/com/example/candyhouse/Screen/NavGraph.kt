package com.example.candyhouse.Screen

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.candyhouse.Screen.ComprasScreen

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
            HistorialScreen()
        }

        composable("detail") {
            DetailScreen(
                onClose = { navController.popBackStack() }
            )
        }

        composable("compras") {
            ComprasScreen(
                viewModel = candyViewModel,
                navController = navController,
                rutaActual = "compras"
            )
        }

        composable("agregar") {
            AddProducts(
                viewModel = candyViewModel,
                navController = navController,
                rutaActual = "agregar"
            )
        }

        composable("notificaciones") {
            NotificacionesScreen(
                viewModel = candyViewModel,
                navController = navController,
                rutaActual = "notificaciones"
            )
        }
    }
}
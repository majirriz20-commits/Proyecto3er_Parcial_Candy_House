package com.example.candyhouse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.candyhouse.Screen.AddProducts
import com.example.candyhouse.Screen.ComprasScreen
import com.example.candyhouse.Screen.NavGraph
import com.example.candyhouse.Screen.NotificacionesScreen
import com.example.candyhouse.ui.theme.CandyHouseTheme
import com.example.candyhouse.viewmodel.CandyViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CandyHouseTheme {
                val navController = rememberNavController()
                NavGraph(navController = navController)
            }
        }
    }
}

@Composable
fun CandyHouseAppNavigation() {
    val navController = rememberNavController()
    val sharedViewModel: CandyViewModel = viewModel()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val rutaActual = navBackStackEntry?.destination?.route ?: "compras"

    NavHost(
        navController = navController,
        startDestination = "compras"
    ) {
        // CARRITO DE COMPRAS
        composable("compras") {
            ComprasScreen(
                viewModel = sharedViewModel,
                navController = navController,
                rutaActual = rutaActual
            )
        }

        // AGREGAR NUEVO PRODUCTO
        composable("agregar") {
            AddProducts(
                viewModel = sharedViewModel,
                navController = navController,
                rutaActual = rutaActual,
                onClose = { navController.popBackStack() } // El botón X regresa de forma segura
            )
        }

        //  NOTIFICACIONES
        composable("notificaciones") {
            NotificacionesScreen(
                viewModel = sharedViewModel,
                navController = navController,
                rutaActual = rutaActual
            )
        }
    }
}

fun gestionarSalto(navController: androidx.navigation.NavController, rutaDestino: String) {
    // Mapeo por si usas nombres alternos en las rutas del NavHost
    val rutaReal = if (rutaDestino == "filtros") "compras" else rutaDestino

    if (navController.currentDestination?.route != rutaReal) {
        navController.navigate(rutaReal) {
            // Limpia la pila para no acumular ventanas infinitas atrás
            popUpTo(navController.graph.startDestinationId) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
}
package com.example.candyhouse.Screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.candyhouse.models.Product

class CandyViewModel : ViewModel() {
    // Aquí se guarda la lista cruda que llega de Node.js
    var listaDesdeApi by mutableStateOf(emptyList<Product>())

    // 🌟 ESTADOS GLOBALES DE LOS FILTROS
    var selectGomitas by mutableStateOf(false)
    var selectChocolates by mutableStateOf(false)
    var selectBebidas by mutableStateOf(false)
    var selectImportados by mutableStateOf(false)

    var rangoPrecio by mutableStateOf(1f..2000f)

    var selectOptimo by mutableStateOf(false)
    var selectBajo by mutableStateOf(false)

    // Lógica inteligente de filtrado reactivo
    val productosFiltrados: List<Product>
        get() {
            var listaFiltrada = listaDesdeApi

            // 1. Filtrar por Categorías
            if (selectGomitas || selectChocolates || selectBebidas || selectImportados) {
                listaFiltrada = listaFiltrada.filter { producto ->
                    val nombre = producto.nombre.lowercase()
                    (selectGomitas && nombre.contains("gomita")) ||
                            (selectChocolates && (nombre.contains("chocolate") || nombre.contains("m&m") || nombre.contains("trufa") || nombre.contains("kisses"))) ||
                            (selectBebidas && (nombre.contains("bebida") || nombre.contains("refresco") || nombre.contains("gatorade"))) ||
                            (selectImportados && nombre.contains("importado"))
                }
            }

            // 2. Filtrar por Rango de Precio
            listaFiltrada = listaFiltrada.filter { producto ->
                producto.precio >= rangoPrecio.start && producto.precio <= rangoPrecio.endInclusive
            }

            // 3. Filtrar por Stock
            if (selectOptimo || selectBajo) {
                listaFiltrada = listaFiltrada.filter { producto ->
                    (selectOptimo && producto.estado == "Optimo") ||
                            (selectBajo && producto.estado == "Bajo")
                }
            }

            return listaFiltrada
        }


    fun limpiarFiltros() {
        selectGomitas = false
        selectChocolates = false
        selectBebidas = false
        selectImportados = false
        rangoPrecio = 1f..2000f
        selectOptimo = false
        selectBajo = false
    }
}
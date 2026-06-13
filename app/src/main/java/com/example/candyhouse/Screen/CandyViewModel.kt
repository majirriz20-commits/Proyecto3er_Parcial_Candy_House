package com.example.candyhouse.Screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.candyhouse.models.Product

class CandyViewModel : ViewModel() {
    var listaDesdeApi by mutableStateOf(emptyList<Product>())

    // Estados de búsqueda
    var textoBusqueda by mutableStateOf("")
    var buscando by mutableStateOf(false)

    // Estados de filtros
    var selectGomitas by mutableStateOf(false)
    var selectChocolates by mutableStateOf(false)
    var selectBebidas by mutableStateOf(false)
    var selectImportados by mutableStateOf(false)
    var rangoPrecio by mutableStateOf(1f..2000f)
    var selectOptimo by mutableStateOf(false)
    var selectBajo by mutableStateOf(false)

    val productosFiltrados: List<Product>
        get() {
            var lista = listaDesdeApi

            // Categorías
            if (selectGomitas || selectChocolates || selectBebidas || selectImportados) {
                lista = lista.filter { p ->
                    val n = p.nombre.lowercase()
                    (selectGomitas && n.contains("gomita")) ||
                            (selectChocolates && (n.contains("chocolate") || n.contains("m&m") || n.contains("trufa") || n.contains("kisses"))) ||
                            (selectBebidas && (n.contains("bebida") || n.contains("refresco") || n.contains("gatorade"))) ||
                            (selectImportados && n.contains("importado"))
                }
            }

            // Precio
            lista = lista.filter { it.precio in rangoPrecio }

            // Stock
            if (selectOptimo || selectBajo) {
                lista = lista.filter { (selectOptimo && it.estado == "Optimo") || (selectBajo && it.estado == "Bajo") }
            }

            return lista
        }

    val productosFiltradosBusqueda: List<Product>
        get() {
            return if (textoBusqueda.isEmpty()) {
                productosFiltrados
            } else {
                productosFiltrados.filter { it.nombre.contains(textoBusqueda, ignoreCase = true) }
            }
        }

    fun limpiarFiltros() {
        selectGomitas = false
        selectChocolates = false
        selectBebidas = false
        selectImportados = false
        rangoPrecio = 1f..2000f
        selectOptimo = false
        selectBajo = false
        textoBusqueda = ""
        buscando = false
    }
}
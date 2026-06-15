package com.example.candyhouse.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.candyhouse.models.Product
import com.example.candyhouse.services.CartRepository
import com.example.candyhouse.services.RetrofitClient
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CandyViewModel : ViewModel() {
    // mandamos traer los datos del servidor de Node.js
    init {
        cargarDulcesDesdeServidor()
    }

    var listaDesdeApi by mutableStateOf(emptyList<Product>())

    //  Ideales para saber qué pasa con la API
    var cargandoApi by mutableStateOf(false)
    var errorApi by mutableStateOf<String?>(null)

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

    var proveedorSeleccionado by mutableStateOf("")

    //  Consumir la API de Node.js en segundo plano
    fun cargarDulcesDesdeServidor() {
        viewModelScope.launch {
            cargandoApi = true
            errorApi = null
            try {
                // Hacemos la petición HTTP a través de Retrofit
                val dulcesRemotos = RetrofitClient.apiService.getDulces()
                listaDesdeApi = dulcesRemotos
            } catch (e: Exception) {
                errorApi = "No se pudo conectar con el servidor: ${e.localizedMessage}"
                e.printStackTrace()
            } finally {
                cargandoApi = false
            }
        }
    }

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

            // Proveedor
            if (proveedorSeleccionado.isNotEmpty()) {
                lista = lista.filter { it.proveedor == proveedorSeleccionado }
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
        proveedorSeleccionado = ""
        textoBusqueda = ""
        buscando = false
    }

    // Funcion exclusivamente para carrito de compras
    val cartItem = CartRepository.cartItems.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )
}
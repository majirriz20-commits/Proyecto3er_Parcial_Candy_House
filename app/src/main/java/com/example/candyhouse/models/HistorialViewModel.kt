package com.example.candyhouse.models


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.candyhouse.Screen.MovimientoHistorial
import com.example.candyhouse.services.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HistorialViewModel : ViewModel() {

    private val _movimientos = MutableStateFlow<List<MovimientoHistorial>>(emptyList())
    val movimientos: StateFlow<List<MovimientoHistorial>> = _movimientos

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        cargarHistorial()
    }

    fun cargarHistorial() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val historialRemoto = RetrofitClient.apiService.getHistorial()
                // La API no devuelve un id (se excluye el _id de Mongo),
                // así que generamos uno local con el índice de la lista.
                _movimientos.value = historialRemoto.mapIndexed { index, item ->
                    MovimientoHistorial(
                        id = index + 1,
                        producto = item.producto,
                        accion = item.accion,
                        cantidad = item.cantidad,
                        fecha = item.fecha
                    )
                }
            } catch (e: Exception) {
                _error.value = "Error al cargar el historial: ${e.localizedMessage}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
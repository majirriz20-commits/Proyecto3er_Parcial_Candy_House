package com.example.candyhouse.Screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HistorialViewModel : ViewModel() {

    // Estado de la pantalla
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
                // Aquí se conectará la API cuando esté lista
                // Por ahora usamos datos de ejemplo
                _movimientos.value = datosEjemplo
            } catch (e: Exception) {
                _error.value = "Error al cargar el historial: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
package com.example.candyhouse.models

//Exclusivo para la ventana de Compras
data class CartItem(
    val id: Int,
    val product: Product,
    val cantidadSeleccionada: Double,
    val isChecked: Boolean = true
)
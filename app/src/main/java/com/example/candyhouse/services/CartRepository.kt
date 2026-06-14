package com.example.candyhouse.services

import com.example.candyhouse.models.CartItem
import com.example.candyhouse.models.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

object CartRepository {
    private val _cartItems= MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems.asStateFlow()

    //Funcion de venta en Detail
    fun addProductCart(product: Product, cantidad: Double){
        _cartItems.update { currentList ->
            //Verifica si el producto ya esta en el carrito buscandolo por ID
            val existingItem = currentList.find {it.product.id == product.id}
            if (existingItem != null) {
                currentList.map {
                    if (it.product.id == product.id) {
                        it.copy(cantidadSeleccionada = it.cantidadSeleccionada + cantidad)
                    } else it
                }
            } else {
                val newId = (currentList.maxOfOrNull {it.id} ?: 0) + 1
                currentList + CartItem (
                    id = newId,
                    product = product,
                    cantidadSeleccionada = cantidad
                )
            }
        }
    }
}
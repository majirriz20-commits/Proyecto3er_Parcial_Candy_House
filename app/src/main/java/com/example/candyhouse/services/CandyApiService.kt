package com.example.candyhouse.services

import com.example.candyhouse.models.Product
import retrofit2.http.*

// Body para venta
data class VentaRequest(val dulceId: Int, val cantidad: Int)

// Body para agregar al carrito
data class PedidoRequest(val dulceId: Int, val cantidad: Int)

// Body para confirmar compra
data class CompraRequest(val metodoPago: String)

// Body para crear un dulce nuevo (POST /api/dulces)
data class DulceRequest(
    val nombre: String,
    val precio: Double,
    val estado: String,
    val imageUrl: String? = null,
    val proveedor: String? = null,
    val existencia: String? = null,
    val pasillo: String? = null,
    val fechaCaducidad: String? = null,
    val categoria: String? = null
)

// Respuesta del carrito
data class CarritoItem(
    val dulceId: Int,
    val nombre: String,
    val precio: Double,
    val cantidad: Int,
    val proveedor: String
)

data class CarritoResponse(
    val carrito: List<CarritoItem>,
    val total: Double
)

// Respuesta de un movimiento de historial (GET /api/historial)
data class HistorialItem(
    val producto: String,
    val accion: String,
    val cantidad: String,
    val fecha: String
)

interface CandyApiService {
    @GET("api/dulces")
    suspend fun getDulces(): List<Product>

    @GET("api/dulces/{id}")
    suspend fun getDulcePorId(@Path("id") id: Int): Product

    // Crear nuevo dulce
    @POST("api/dulces")
    suspend fun crearDulce(@Body body: DulceRequest): Product

    // Registrar venta (reduce existencia)
    @POST("api/ventas")
    suspend fun registrarVenta(@Body body: VentaRequest): retrofit2.Response<Any>

    // Agregar al carrito global
    @POST("api/pedidos/agregar")
    suspend fun agregarAlCarrito(@Body body: PedidoRequest): CarritoResponse

    // Obtener carrito actual
    @GET("api/pedidos")
    suspend fun getCarrito(): CarritoResponse

    // Limpiar carrito
    @DELETE("api/pedidos/limpiar")
    suspend fun limpiarCarrito(): retrofit2.Response<Any>

    // Confirmar compra
    @POST("api/compras/confirmar")
    suspend fun confirmarCompra(@Body body: CompraRequest): retrofit2.Response<Any>

    // Historial de movimientos
    @GET("api/historial")
    suspend fun getHistorial(): List<HistorialItem>
}
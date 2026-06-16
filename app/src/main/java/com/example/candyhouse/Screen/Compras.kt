package com.example.candyhouse.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.candyhouse.models.CartItem
import com.example.candyhouse.ui.theme.BackgroundCandy
import com.example.candyhouse.viewmodel.CandyViewModel
import java.util.Locale
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.candyhouse.components.CandyBottomBar
import com.example.candyhouse.gestionarSalto
import com.example.candyhouse.services.CarritoItem
import com.example.candyhouse.services.CompraRequest
import com.example.candyhouse.services.PedidoRequest
import com.example.candyhouse.services.RetrofitClient
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComprasScreen(
    viewModel: CandyViewModel = viewModel(),
    navController: NavHostController,
    rutaActual: String
) {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    var carritoItems by remember { mutableStateOf<List<CarritoItem>>(emptyList()) }
    var total by remember { mutableStateOf(0.0) }
    var cargando by remember { mutableStateOf(true) }
    var selectedPaymentMethod by remember { mutableStateOf("Efectivo") }

    // Cargar carrito al entrar
    fun cargarCarrito() {
        scope.launch {
            cargando = true
            try {
                val response = RetrofitClient.apiService.getCarrito()
                carritoItems = response.carrito
                total = response.total
            } catch (e: Exception) {
                snackbarHostState.showSnackbar("Error al cargar el carrito")
            } finally {
                cargando = false
            }
        }
    }

    LaunchedEffect(Unit) { cargarCarrito() }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    Text("Productos Seleccionados", fontSize = 24.sp,
                        fontWeight = FontWeight.Bold, color = Color.Black)
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BackgroundCandy)
            )
        },
        bottomBar = {
            CandyBottomBar(
                pantallaActual = "filtros",
                onTabSelected = { destino -> gestionarSalto(navController, destino) }
            )
        },
        containerColor = BackgroundCandy
    ) { innerPadding ->

        when {
            cargando -> {
                Box(Modifier.fillMaxSize().padding(innerPadding), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Color(0xFFFF4081))
                }
            }
            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    if (carritoItems.isEmpty()) {
                        item {
                            Box(Modifier.fillMaxWidth().padding(top = 64.dp),
                                contentAlignment = Alignment.Center) {
                                Text("El carrito está vacío", color = Color.Gray, fontSize = 16.sp)
                            }
                        }
                    } else {
                        items(carritoItems) { item ->
                            CarritoItemRow(
                                item = item,
                                onEliminar = {
                                    scope.launch {
                                        try {
                                            // La API solo tiene "limpiar TODO el carrito",
                                            // así que para borrar un solo producto:
                                            // 1) limpiamos el carrito completo en el servidor
                                            // 2) volvemos a agregar todos los productos
                                            //    excepto el que se está eliminando
                                            val itemsRestantes = carritoItems.filter { it.dulceId != item.dulceId }

                                            RetrofitClient.apiService.limpiarCarrito()
                                            itemsRestantes.forEach { restante ->
                                                RetrofitClient.apiService.agregarAlCarrito(
                                                    PedidoRequest(
                                                        dulceId = restante.dulceId,
                                                        cantidad = restante.cantidad
                                                    )
                                                )
                                            }

                                            cargarCarrito()
                                            snackbarHostState.showSnackbar("${item.nombre} eliminado del carrito")
                                        } catch (e: Exception) {
                                            cargarCarrito() // por si quedó en un estado intermedio
                                            snackbarHostState.showSnackbar("Error al eliminar el producto")
                                        }
                                    }
                                }
                            )
                        }

                        item {
                            Spacer(modifier = Modifier.height(8.dp))

                            // Total
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("Total", fontSize = 22.sp, color = Color.Gray)
                                Text(
                                    text = "$${String.format(Locale.US, "%.2f", total)}",
                                    fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.Black
                                )
                            }

                            Spacer(modifier = Modifier.height(16.dp))
                            Text("Forma de pago", fontSize = 15.sp,
                                fontWeight = FontWeight.Bold, color = Color.Black,
                                modifier = Modifier.padding(bottom = 8.dp))

                            // Efectivo
                            Surface(
                                onClick = { selectedPaymentMethod = "Efectivo" },
                                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp).height(56.dp),
                                shape = RoundedCornerShape(12.dp),
                                border = androidx.compose.foundation.BorderStroke(1.dp, Color.LightGray),
                                color = Color.White
                            ) {
                                Row(Modifier.fillMaxSize().padding(horizontal = 16.dp),
                                    verticalAlignment = Alignment.CenterVertically) {
                                    RadioButton(
                                        selected = selectedPaymentMethod == "Efectivo",
                                        onClick = { selectedPaymentMethod = "Efectivo" },
                                        colors = RadioButtonDefaults.colors(selectedColor = Color.Gray)
                                    )
                                    Spacer(Modifier.width(12.dp))
                                    Text("Efectivo", fontSize = 16.sp, color = Color.Black)
                                }
                            }

                            // Tarjeta
                            Surface(
                                onClick = { selectedPaymentMethod = "Tarjeta" },
                                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp).height(56.dp),
                                shape = RoundedCornerShape(12.dp),
                                border = androidx.compose.foundation.BorderStroke(1.dp, Color.LightGray),
                                color = Color.White
                            ) {
                                Row(Modifier.fillMaxSize().padding(horizontal = 16.dp),
                                    verticalAlignment = Alignment.CenterVertically) {
                                    RadioButton(
                                        selected = selectedPaymentMethod == "Tarjeta",
                                        onClick = { selectedPaymentMethod = "Tarjeta" },
                                        colors = RadioButtonDefaults.colors(selectedColor = Color.Gray)
                                    )
                                    Spacer(Modifier.width(12.dp))
                                    Text("Tarjeta", fontSize = 16.sp, color = Color.Black)
                                }
                            }

                            Spacer(modifier = Modifier.height(24.dp))

                            // Botón pagar
                            Button(
                                onClick = {
                                    scope.launch {
                                        try {
                                            RetrofitClient.apiService.confirmarCompra(
                                                CompraRequest(metodoPago = selectedPaymentMethod)
                                            )
                                            carritoItems = emptyList()
                                            total = 0.0
                                            snackbarHostState.showSnackbar("¡Compra confirmada! Existencias actualizadas 🍬")
                                        } catch (e: Exception) {
                                            snackbarHostState.showSnackbar("Error al confirmar compra")
                                        }
                                    }
                                },
                                modifier = Modifier.fillMaxWidth().height(54.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFD600)),
                                shape = RoundedCornerShape(16.dp)
                            ) {
                                Text(
                                    text = "Pagar (${carritoItems.size} productos)",
                                    color = Color.Black, fontSize = 18.sp, fontWeight = FontWeight.Bold
                                )
                            }
                            Spacer(modifier = Modifier.height(24.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CarritoItemRow(item: CarritoItem, onEliminar: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFFE2E2E2)),
            contentAlignment = Alignment.Center
        ) {
            Text(item.nombre.take(2), fontSize = 24.sp, color = Color.Gray)
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(item.nombre, fontSize = 16.sp, fontWeight = FontWeight.Medium, color = Color.Black)
            Text("$${item.precio}", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.Black,
                modifier = Modifier.padding(vertical = 2.dp))
            Text("Cantidad: ${item.cantidad}", fontSize = 13.sp, color = Color.Gray)
            Text("Total: $${String.format(Locale.US, "%.2f", item.precio * item.cantidad)}",
                fontSize = 13.sp, color = Color(0xFFFF4081), fontWeight = FontWeight.SemiBold)

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = onEliminar,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF4081)),
                modifier = Modifier.height(32.dp),
                shape = RoundedCornerShape(12.dp),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp)
            ) {
                Icon(Icons.Default.Delete, null, tint = Color.White, modifier = Modifier.size(14.dp))
                Spacer(Modifier.width(4.dp))
                Text("Eliminar", color = Color.White, fontSize = 12.sp)
            }
        }
    }
}
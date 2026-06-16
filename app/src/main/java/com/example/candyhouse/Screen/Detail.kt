package com.example.candyhouse.Screen

import coil3.compose.AsyncImage
import androidx.compose.ui.layout.ContentScale
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.candyhouse.models.Product
import com.example.candyhouse.services.CartRepository
import com.example.candyhouse.services.PedidoRequest
import com.example.candyhouse.services.RetrofitClient
import com.example.candyhouse.services.VentaRequest
import kotlinx.coroutines.launch

@Composable
fun DetailScreen(
    producto: Product = Product(
        id = 1,
        nombre = "Gomitas de oso",
        precio = 15.00,
        estado = "Optimo",
        categoria = "Gomitas",
        imageUrl = "",
        proveedor = "Dulcería Denny",
        existencia = "150 pz",
        pasillo = "B-04",
        fechaCaducidad = "05/2028",
        cantidad = 150.0
    ),
    onClose: () -> Unit = {}
) {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    var cantidad by remember { mutableStateOf(1) }
    var cargando by remember { mutableStateOf(false) }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = Color.White
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color.White)
                .verticalScroll(rememberScrollState())
        ) {
            // Imagen del producto
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(260.dp)
                    .background(Color(0xFFE0E0E0))
            ) {
                IconButton(
                    onClick = onClose,
                    modifier = Modifier
                        .padding(12.dp)
                        .align(Alignment.TopStart)
                        .size(36.dp)
                        .background(Color.White.copy(alpha = 0.8f), CircleShape)
                ) {
                    Icon(Icons.Default.Close, contentDescription = "Cerrar",
                        tint = Color.DarkGray, modifier = Modifier.size(20.dp))
                }
                AsyncImage(
                    model = producto.imageUrl,
                    contentDescription = producto.nombre,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
                Row(
                    modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(Color.Gray))
                    Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(Color.DarkGray))
                }
            }

            // Nombre e ID
            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = producto.nombre, fontSize = 26.sp,
                        fontWeight = FontWeight.Bold, color = Color.Black)
                    Text(text = "ID: ${producto.id}", fontSize = 13.sp, color = Color.Gray)
                }
                IconButton(onClick = { }) {
                    Icon(Icons.Default.Edit, contentDescription = "Editar",
                        tint = Color(0xFFFF3B7B), modifier = Modifier.size(24.dp))
                }
                IconButton(onClick = { }) {
                    Icon(Icons.Default.Delete, contentDescription = "Eliminar",
                        tint = Color(0xFFFFC107), modifier = Modifier.size(24.dp))
                }
            }

            // Chips de info
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                InfoChip("🏷️", "Precio", "$${producto.precio}", Color(0xFF29B6F6))
                InfoChip("📦", "Existencia", producto.existencia, Color(0xFF66BB6A))
                InfoChip("📍", "Pasillo", producto.pasillo, Color(0xFFFF9800))
            }

            Spacer(modifier = Modifier.height(20.dp))
            HorizontalDivider(color = Color(0xFFEEEEEE))
            Spacer(modifier = Modifier.height(16.dp))

            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Text("Proveedor", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(2.dp))
                Text(producto.proveedor, fontSize = 14.sp, color = Color.DarkGray)
            }
            Spacer(modifier = Modifier.height(12.dp))
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Text("Categoría", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(2.dp))
                Text(producto.categoria.ifBlank { "Sin categoría" }, fontSize = 14.sp, color = Color.DarkGray)
            }
            Spacer(modifier = Modifier.height(12.dp))
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Text("Fecha de caducidad", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(2.dp))
                Text(producto.fechaCaducidad, fontSize = 14.sp, color = Color.DarkGray)
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Selector de cantidad
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Cantidad:", fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    IconButton(
                        onClick = { if (cantidad > 1) cantidad-- },
                        modifier = Modifier.size(32.dp).border(1.5.dp, Color(0xFFFF3B7B), CircleShape)
                    ) {
                        Text("−", fontSize = 18.sp, color = Color(0xFFFF3B7B), fontWeight = FontWeight.Bold)
                    }
                    Box(
                        modifier = Modifier.border(1.dp, Color.LightGray, RoundedCornerShape(4.dp))
                            .padding(horizontal = 16.dp, vertical = 4.dp)
                    ) {
                        Text(text = cantidad.toString(), fontSize = 14.sp)
                    }
                    IconButton(
                        onClick = { cantidad++ },
                        modifier = Modifier.size(32.dp).border(1.5.dp, Color(0xFFFF3B7B), CircleShape)
                    ) {
                        Text("+", fontSize = 18.sp, color = Color(0xFFFF3B7B), fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Botones Venta y Pedir
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // VENTA: reduce existencia en la API y registra historial
                Button(
                    onClick = {
                        scope.launch {
                            cargando = true
                            try {
                                RetrofitClient.apiService.registrarVenta(
                                    VentaRequest(dulceId = producto.id, cantidad = cantidad)
                                )
                                snackbarHostState.showSnackbar("Venta registrada: -$cantidad pz de ${producto.nombre}")
                            } catch (e: Exception) {
                                snackbarHostState.showSnackbar("Error al registrar venta")
                            } finally {
                                cargando = false
                            }
                        }
                    },
                    enabled = !cargando,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFC107)),
                    shape = RoundedCornerShape(24.dp),
                    modifier = Modifier.weight(1f).height(48.dp)
                ) {
                    Text("🛒  Venta", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                }

                // PEDIR: agrega al carrito global de la API
                Button(
                    onClick = {
                        scope.launch {
                            cargando = true
                            try {
                                RetrofitClient.apiService.agregarAlCarrito(
                                    PedidoRequest(dulceId = producto.id, cantidad = cantidad)
                                )
                                snackbarHostState.showSnackbar("${producto.nombre} agregado al carrito ($cantidad pz)")
                            } catch (e: Exception) {
                                snackbarHostState.showSnackbar("Error al agregar al carrito")
                            } finally {
                                cargando = false
                            }
                        }
                    },
                    enabled = !cargando,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00D4FF)),
                    shape = RoundedCornerShape(24.dp),
                    modifier = Modifier.weight(1f).height(48.dp)
                ) {
                    if (cargando) {
                        CircularProgressIndicator(color = Color.White, modifier = Modifier.size(18.dp), strokeWidth = 2.dp)
                    } else {
                        Text("⊕  Pedir", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

@Composable
fun InfoChip(emoji: String, label: String, value: String, chipColor: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(chipColor.copy(alpha = 0.15f))
                .padding(horizontal = 12.dp, vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(emoji, fontSize = 20.sp)
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(label, fontSize = 10.sp, color = Color.Gray)
        Text(value, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
    }
}

@Preview(showBackground = true)
@Composable
fun DetailScreenPreview() {
    DetailScreen()
}
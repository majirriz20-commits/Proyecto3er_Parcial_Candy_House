package com.example.candyhouse.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import models.HistorialViewModel

data class MovimientoHistorial(
    val id: Int,
    val producto: String,
    val accion: String,
    val cantidad: String,
    val fecha: String
)


    val datosEjemplo = listOf(
    MovimientoHistorial(1,  "Gomitas de osito", "Venta",   "-14 pz", "hoy 14:30"),
    MovimientoHistorial(2,  "Paleta payaso",    "Surtido", "+4 pz",  "05/06/2026"),
    MovimientoHistorial(3,  "Chocolate",        "Ajuste",  "-14 pz", "04/06/2026"),
    MovimientoHistorial(4,  "Gomitas de osito", "Venta",   "-14 pz", "hoy 14:30"),
    MovimientoHistorial(5,  "Paleta payaso",    "Surtido", "+4 pz",  "05/06/2026"),
    MovimientoHistorial(6,  "Chocolate",        "Ajuste",  "-14 pz", "04/06/2026"),
    MovimientoHistorial(7,  "Gomitas de osito", "Venta",   "-14 pz", "hoy 14:30"),
    MovimientoHistorial(8,  "Paleta payaso",    "Surtido", "+4 pz",  "05/06/2026"),
    MovimientoHistorial(9,  "Chocolate",        "Ajuste",  "-14 pz", "04/06/2026"),
    MovimientoHistorial(10, "Chocolate",        "Ajuste",  "-14 pz", "04/06/2026"),
)

@Composable
fun HistorialScreen(
    viewModel: HistorialViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    navController: androidx.navigation.NavHostController = androidx.navigation.compose.rememberNavController()
) {
    val movimientos by viewModel.movimientos.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()
    Scaffold(
        bottomBar = {
            com.example.candyhouse.components.CandyBottomBar(
                pantallaActual = "historial",
                onTabSelected = { destino ->
                    com.example.candyhouse.gestionarSalto(
                        navController,
                        destino
                    )
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Historial",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(20.dp))

            // ── Encabezado de tabla ────────────────────────────────────
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "🍬 Producto",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1.2f)
                )
                Text(
                    "✏️ Acción",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(0.8f)
                )
                Text(
                    "📦 Cantidad",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(0.9f)
                )
                Text(
                    "📅 Fecha",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(0.9f)
                )
            }

            HorizontalDivider(color = Color(0xFFEEEEEE))

            // ── Lista de movimientos ───────────────────────────────────
            // ── Estados: carga / error / vacío / datos ─────────────────
            when {
                isLoading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = Color(0xFFFF3B7B))
                    }
                }

                error != null -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("⚠️", fontSize = 40.sp)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                "No se pudo cargar el historial",
                                color = Color.Gray,
                                fontSize = 14.sp
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(error ?: "", color = Color.LightGray, fontSize = 12.sp)
                        }
                    }
                }

                movimientos.isEmpty() -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("📋", fontSize = 40.sp)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("Sin movimientos todavía", color = Color.Gray, fontSize = 14.sp)
                        }
                    }
                }

                else -> {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(movimientos, key = { it.id }) { movimiento ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                val accionColor = when (movimiento.accion) {
                                    "Venta" -> Color(0xFFFF3B7B)
                                    "Surtido" -> Color(0xFF00D4FF)
                                    "Ajuste" -> Color(0xFFFFC107)
                                    else -> Color.Gray
                                }
                                val cantidadColor = if (movimiento.cantidad.startsWith("+"))
                                    Color(0xFF4CAF50) else Color(0xFFE53935)

                                Text(
                                    movimiento.producto,
                                    fontSize = 13.sp,
                                    modifier = Modifier.weight(1.2f),
                                    maxLines = 2
                                )
                                Text(
                                    movimiento.accion,
                                    fontSize = 12.sp,
                                    modifier = Modifier.weight(0.8f),
                                    color = accionColor,
                                    fontWeight = FontWeight.Medium
                                )
                                Text(
                                    movimiento.cantidad,
                                    fontSize = 12.sp,
                                    modifier = Modifier.weight(0.9f),
                                    color = cantidadColor,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Text(
                                    movimiento.fecha,
                                    fontSize = 11.sp,
                                    modifier = Modifier.weight(0.9f),
                                    color = Color.Gray
                                )
                            }
                            HorizontalDivider(color = Color(0xFFF5F5F5))
                        }
                        item { Spacer(modifier = Modifier.height(80.dp)) }
                    }
                }
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun HistorialScreenPreview() {
        HistorialScreen()
    }
}
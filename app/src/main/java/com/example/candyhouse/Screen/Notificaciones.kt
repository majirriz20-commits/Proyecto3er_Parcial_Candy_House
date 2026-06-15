package com.example.candyhouse.Screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.HourglassBottom
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.candyhouse.components.CandyBottomBar
import com.example.candyhouse.viewmodel.CandyViewModel
import com.example.candyhouse.services.RetrofitClient
import com.example.candyhouse.models.Product
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.candyhouse.gestionarSalto
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificacionesScreen(
    viewModel: CandyViewModel = viewModel(),
    navController: NavHostController,
    rutaActual: String
) {
    val scope = rememberCoroutineScope()

    var productos by remember { mutableStateOf<List<Product>>(emptyList()) }
    var cargando  by remember { mutableStateOf(true) }
    var errorMsg  by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        scope.launch {
            try {
                productos = RetrofitClient.apiService.getDulces()
            } catch (e: Exception) {
                errorMsg = "Sin conexión con la API"
            } finally {
                cargando = false
            }
        }
    }

    val bajoStock     = productos.filter { it.estado == "Bajo" }
    val proximoVencer = productos.filter {
        val anio = it.fechaCaducidad.substringAfter("/").trim().toIntOrNull() ?: 9999
        anio <= 2027
    }

    Scaffold(
        bottomBar = {
            CandyBottomBar(
                pantallaActual = "notificaciones",
                onTabSelected = { destino -> gestionarSalto(navController, destino) }
            )
        },
        containerColor = Color.White
    ) { innerPadding ->

        when {
            cargando -> {
                Box(Modifier.fillMaxSize().padding(innerPadding), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        CircularProgressIndicator(color = Color(0xFFFF3B7B))
                        Spacer(Modifier.height(12.dp))
                        Text("Revisando inventario...", color = Color.Gray, fontSize = 14.sp)
                    }
                }
            }

            errorMsg != null -> {
                Box(Modifier.fillMaxSize().padding(innerPadding), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(32.dp)) {
                        Text("⚠️", fontSize = 40.sp)
                        Spacer(Modifier.height(12.dp))
                        Text("No se pudo conectar con la API", color = Color.Black, fontSize = 15.sp, fontWeight = FontWeight.Medium)
                        Text("Verifica que el servidor esté corriendo", color = Color.Gray, fontSize = 13.sp)
                        Spacer(Modifier.height(20.dp))
                        Button(
                            onClick = {
                                cargando = true; errorMsg = null
                                scope.launch {
                                    try { productos = RetrofitClient.apiService.getDulces() }
                                    catch (e: Exception) { errorMsg = "Sin conexión" }
                                    finally { cargando = false }
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF3B7B)),
                            shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
                        ) { Text("Reintentar", color = Color.White) }
                    }
                }
            }

            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(horizontal = 24.dp)
                ) {
                    item {
                        Spacer(modifier = Modifier.height(24.dp))
                        Text("Notificaciones", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                        Spacer(modifier = Modifier.height(24.dp))
                    }

                    // Bajo Stock
                    item { NotificationHeader(title = "Bajo Stock", icon = Icons.Filled.Warning, iconColor = Color(0xFFFF4757)) }
                    item { Spacer(modifier = Modifier.height(12.dp)) }
                    item {
                        if (bajoStock.isEmpty()) {
                            Text("Sin productos con bajo stock 🎉", color = Color.Gray, fontSize = 14.sp)
                        } else {
                            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                                bajoStock.forEach { p ->
                                    NotificationRow(name = p.nombre, value = p.existencia)
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(28.dp))
                    }

                    // Próximo a vencer
                    item { NotificationHeader(title = "Próximo a vencer", icon = Icons.Filled.HourglassBottom, iconColor = Color(0xFFFFD93D)) }
                    item { Spacer(modifier = Modifier.height(12.dp)) }
                    item {
                        if (proximoVencer.isEmpty()) {
                            Text("Sin productos próximos a vencer 🎉", color = Color.Gray, fontSize = 14.sp)
                        } else {
                            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                                proximoVencer.forEach { p ->
                                    NotificationRow(name = p.nombre, value = p.fechaCaducidad)
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(32.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun NotificationHeader(title: String, icon: ImageVector, iconColor: Color) {
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        Icon(imageVector = icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(26.dp))
        Text(text = title, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Black)
    }
}

@Composable
fun NotificationRow(name: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = name, fontSize = 15.sp, color = Color.DarkGray)
        Text(text = value, fontSize = 14.sp, color = Color.Gray)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun NotificacionesPreview() {
    Scaffold(containerColor = Color.White) { pad ->
        LazyColumn(modifier = Modifier.fillMaxSize().padding(pad).padding(horizontal = 24.dp)) {
            item {
                Spacer(Modifier.height(24.dp))
                Text("Notificaciones", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                Spacer(Modifier.height(24.dp))
                NotificationHeader("Bajo Stock", Icons.Filled.Warning, Color(0xFFFF4757))
                Spacer(Modifier.height(12.dp))
            }
            item {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    NotificationRow("Paleta Payaso", "45 pz")
                    NotificationRow("Chocolates M&M", "12 pz")
                    NotificationRow("Paletas de Cereza", "20 pz")
                }
                Spacer(Modifier.height(28.dp))
                NotificationHeader("Próximo a vencer", Icons.Filled.HourglassBottom, Color(0xFFFFD93D))
                Spacer(Modifier.height(12.dp))
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    NotificationRow("Paleta Payaso", "10/2027")
                    NotificationRow("Paletas de Cereza", "04/2027")
                }
            }
        }
    }
}
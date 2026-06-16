package com.example.candyhouse.Screen

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil3.compose.AsyncImage
import com.example.candyhouse.components.CandyBottomBar
import com.example.candyhouse.viewmodel.CandyViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.candyhouse.gestionarSalto
import com.example.candyhouse.services.DulceRequest
import com.example.candyhouse.services.RetrofitClient
import kotlinx.coroutines.launch


@SuppressLint("InvalidColorHexValue")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProducts(
    onClose: () -> Unit = {},
    viewModel: CandyViewModel = viewModel(),
    navController: NavHostController,
    rutaActual: String
){
    var titulo by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var existencia by remember { mutableStateOf("") }
    var pasillo by remember { mutableStateOf("") }
    var proveedor by remember { mutableStateOf("") }
    var categoria by remember { mutableStateOf("") }
    var fechaCaducidad by remember { mutableStateOf("") }
    var imageUrl by remember { mutableStateOf("") }
    var showSuccessDialog by remember { mutableStateOf(false) }
    var showImageDialog by remember { mutableStateOf(false) }
    var imageUrlTemp by remember { mutableStateOf("") }
    var guardando by remember { mutableStateOf(false) }
    var errorGuardado by remember { mutableStateOf<String?>(null) }

    val scope = rememberCoroutineScope()

    Scaffold(
        bottomBar = {
            CandyBottomBar(
                pantallaActual = "agregar",
                onTabSelected = { destino -> gestionarSalto(navController, destino) }
            )
        },
        containerColor = Color.White
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
            ) {
                // CAJA DE IMAGEN
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                        .background(Color(0xFFE0E0E0))
                        .clickable {
                            imageUrlTemp = imageUrl
                            showImageDialog = true
                        },
                    contentAlignment = Alignment.Center
                ) {
                    if (imageUrl.isNotEmpty()) {
                        AsyncImage(
                            model = imageUrl,
                            contentDescription = "Imagen del producto",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Fit
                        )
                    } else {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Box(
                                modifier = Modifier
                                    .size(80.dp)
                                    .border(2.dp, Color.White, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(40.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(text = "Agregar Imagen", color = Color.White, fontSize = 14.sp)
                        }
                    }

                    // Botón cerrar SIEMPRE en esquina superior izquierda
                    IconButton(
                        onClick = onClose,
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(8.dp)
                            .size(32.dp)
                            .background(Color.White.copy(alpha = 0.7f), CircleShape)
                    ) {
                        Icon(Icons.Default.Close, contentDescription = "Cerrar", tint = Color.DarkGray, modifier = Modifier.size(18.dp))
                    }

                    // Puntos solo cuando no hay imagen
                    if (imageUrl.isEmpty()) {
                        Row(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(bottom = 12.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(Color.White))
                            Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(Color.Gray))
                        }
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    Text(text = "Nuevo Producto", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                    Spacer(modifier = Modifier.height(16.dp))

                    CandyInputField(label = "Título", icon = "🍬", value = titulo, onValueChange = { titulo = it }, placeholder = "Nombre del producto", iconColor = Color(0xFFFF4081))
                    CandyInputField(label = "Precio", icon = "🏷️", value = precio, onValueChange = { precio = it }, placeholder = "$ 0.00", iconColor = Color(0xFF0095FF))
                    CandyInputField(label = "Existencia", icon = "📦", value = existencia, onValueChange = { existencia = it }, placeholder = "Stock", iconColor = Color(0xFFFFD600))
                    CandyInputField(label = "Pasillo", icon = "📍", value = pasillo, onValueChange = { pasillo = it }, placeholder = "Número", iconColor = Color(0xFFFF4081))
                    CandyInputField(label = "Proveedor", icon = "👤", value = proveedor, onValueChange = { proveedor = it }, placeholder = "Nombre", iconColor = Color(0xFF0095FF))

                    CategoriaSelector(categoriaSeleccionada = categoria, onSeleccionar = { categoria = it })

                    CandyInputField(label = "Fecha de caducidad", icon = "📅", value = fechaCaducidad, onValueChange = { fechaCaducidad = it }, placeholder = "MM/AAAA", iconColor = Color(0xFFFF4081))

                    errorGuardado?.let {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(it, color = Color(0xFFE53935), fontSize = 13.sp)
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = {
                            val precioDouble = precio.toDoubleOrNull()
                            if (titulo.isBlank() || precioDouble == null) {
                                errorGuardado = "Completa al menos el título y un precio válido"
                                return@Button
                            }

                            scope.launch {
                                guardando = true
                                errorGuardado = null
                                try {
                                    val existenciaNum = existencia.filter { it.isDigit() }.toIntOrNull() ?: 0
                                    val existenciaFormateada =
                                        if (existencia.isNotBlank() && existencia.all { it.isDigit() }) "$existencia pz"
                                        else existencia
                                    val estadoCalculado = if (existenciaNum <= 50) "Bajo" else "Optimo"

                                    RetrofitClient.apiService.crearDulce(
                                        DulceRequest(
                                            nombre = titulo,
                                            precio = precioDouble,
                                            estado = estadoCalculado,
                                            imageUrl = imageUrl.ifBlank { null },
                                            proveedor = proveedor.ifBlank { null },
                                            existencia = existenciaFormateada.ifBlank { null },
                                            pasillo = pasillo.ifBlank { null },
                                            fechaCaducidad = fechaCaducidad.ifBlank { null },
                                            categoria = categoria.ifBlank { null }
                                        )
                                    )

                                    // Refresca la lista de Inicio con el nuevo producto
                                    viewModel.cargarDulcesDesdeServidor()
                                    showSuccessDialog = true

                                    // Limpiar formulario
                                    titulo = ""; precio = ""; existencia = ""; pasillo = ""
                                    proveedor = ""; categoria = ""; fechaCaducidad = ""; imageUrl = ""
                                } catch (e: Exception) {
                                    errorGuardado = "No se pudo guardar el producto: ${e.localizedMessage}"
                                } finally {
                                    guardando = false
                                }
                            }
                        },
                        enabled = !guardando,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFD600)),
                        modifier = Modifier
                            .align(Alignment.End)
                            .width(140.dp)
                            .height(45.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        if (guardando) {
                            CircularProgressIndicator(modifier = Modifier.size(20.dp), color = Color.Black, strokeWidth = 2.dp)
                        } else {
                            Text("Guardar", color = Color.Black, fontWeight = FontWeight.Bold)
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))
                }
            }

            // DIALOG PARA URL DE IMAGEN
            if (showImageDialog) {
                Dialog(onDismissRequest = { showImageDialog = false }) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(24.dp))
                            .background(Color.White)
                            .padding(24.dp)
                    ) {
                        Text(
                            if (imageUrl.isEmpty()) "Agregar imagen" else "Editar imagen",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        OutlinedTextField(
                            value = imageUrlTemp,
                            onValueChange = { imageUrlTemp = it },
                            placeholder = { Text("https://...", color = Color.LightGray) },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp),
                            maxLines = 3,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color(0xFFFF4081),
                                unfocusedBorderColor = Color.LightGray
                            )
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Button(
                                onClick = {
                                    imageUrlTemp = ""
                                    showImageDialog = false
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text("Cancelar", color = Color.Black)
                            }
                            Button(
                                onClick = {
                                    imageUrl = imageUrlTemp
                                    showImageDialog = false
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF4081)),
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text(
                                    if (imageUrl.isEmpty()) "Agregar" else "Editar",
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
            }

            // DIALOG DE ÉXITO
            if (showSuccessDialog) {
                Dialog(onDismissRequest = { showSuccessDialog = false }) {
                    Box(
                        modifier = Modifier
                            .size(280.dp)
                            .clip(RoundedCornerShape(24.dp))
                            .background(Color.White)
                            .padding(20.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = "Guardado", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                            Spacer(modifier = Modifier.height(20.dp))
                            Box(
                                modifier = Modifier
                                    .size(80.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFF66BB6A)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(imageVector = Icons.Default.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(50.dp))
                            }
                            Spacer(modifier = Modifier.height(20.dp))
                            Text(text = "¡Ha sido guardado con éxito!", fontSize = 16.sp, textAlign = TextAlign.Center, color = Color.DarkGray)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CategoriaSelector(categoriaSeleccionada: String, onSeleccionar: (String) -> Unit) {
    val opciones = listOf("Gomitas", "Chocolates", "Bebidas", "Importados")
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp)) {
        Text("Categoría", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.Black)
        Spacer(modifier = Modifier.height(6.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            opciones.forEach { opcion ->
                val seleccionada = categoriaSeleccionada == opcion
                Surface(
                    onClick = { onSeleccionar(opcion) },
                    shape = RoundedCornerShape(20.dp),
                    color = if (seleccionada) Color(0xFFFF4081) else Color(0xFFF9F9F9),
                    border = BorderStroke(1.dp, if (seleccionada) Color(0xFFFF4081) else Color.LightGray)
                ) {
                    Text(
                        opcion,
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                        color = if (seleccionada) Color.White else Color.Black,
                        fontSize = 13.sp
                    )
                }
            }
        }
    }
}

@Composable
fun CandyInputField(
    label: String,
    icon: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    iconColor: Color
) {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(label, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            Spacer(modifier = Modifier.width(6.dp))
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(iconColor.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Text(icon, fontSize = 13.sp)
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth().height(48.dp),
            placeholder = { Text(placeholder, color = Color.LightGray, fontSize = 14.sp) },
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color.LightGray,
                focusedBorderColor = Color.Black,
                unfocusedContainerColor = Color(0xFFF9F9F9),
                focusedContainerColor = Color(0xFFF9F9F9)
            ),
            singleLine = true
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AddProductPreview() {
    AddProducts(
        navController = rememberNavController(),
        rutaActual = "agregar"
    )
}
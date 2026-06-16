package com.example.candyhouse.Screen

import androidx.compose.material.icons.filled.Sell
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.Image
import androidx.compose.ui.graphics.vector.ImageVector
import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.candyhouse.components.CandyBottomBar
import com.example.candyhouse.viewmodel.CandyViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.candyhouse.gestionarSalto

@SuppressLint("InvalidColorHexValue")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProducts(
    onClose: () -> Unit = {},
    viewModel: CandyViewModel = viewModel(),
    navController: NavHostController,
    rutaActual: String
){
    // Estados para cada campo de texto
    var titulo by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var existencia by remember { mutableStateOf("") }
    var pasillo by remember { mutableStateOf("") }
    var proveedor by remember { mutableStateOf("") }
    var categoria by remember { mutableStateOf("") }
    var fechaCaducidad by remember { mutableStateOf("") }

    // Anuncio de Guardado con éxito
    var showSuccessDialog by remember { mutableStateOf(false) }

    Scaffold(
        bottomBar = {
            CandyBottomBar(
                pantallaActual = "agregar",
                onTabSelected = {destino -> gestionarSalto(navController, destino)}
            )
        },
        containerColor = Color.White
    ) { innerPadding ->
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                        .background(Color(0xFFE0E0E0)),
                    contentAlignment = Alignment.Center
                ) {
                    IconButton(
                        onClick = onClose,
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(16.dp)
                            .size(32.dp)
                            .background(Color.White.copy(alpha = 0.5f), CircleShape)
                    ) {
                        Icon(Icons.Default.Close, contentDescription = "Cerrar", tint = Color.DarkGray)
                    }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .size(80.dp)
                                .border(2.dp, Color.White, CircleShape),
                            contentAlignment = Alignment.Center
                        ){
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(40.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Agregar Imagen",
                            color = Color.White,
                            fontSize = 14.sp
                        )
                    }

                    // Puntos indicadores
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

                // FORMULARIO DE DATOS
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    Text(
                        text = "Título",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Text(
                        text = "01545789",
                        fontSize = 13.sp,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    CandyInputField(label = "Título", icon = Icons.Default.ShoppingBag, value = titulo, onValueChange = { titulo = it }, placeholder = "Nombre del producto", iconColor = Color(0xFFFF4081))
                    CandyInputField(label = "Precio", icon = Icons.Default.Sell, value = precio, onValueChange = { precio = it }, placeholder = "$ 0.00", iconColor = Color(0xFF0095FF))
                    CandyInputField(label = "Existencia", icon = Icons.Default.Inventory, value = existencia, onValueChange = { existencia = it }, placeholder = "Stock", iconColor = Color(0xFFFFD600))
                    CandyInputField(label = "Pasillo", icon = Icons.Default.LocationOn, value = pasillo, onValueChange = { pasillo = it }, placeholder = "Número", iconColor = Color(0xFFFF4081))
                    CandyInputField(label = "Proveedor", icon = Icons.Default.Person, value = proveedor, onValueChange = { proveedor = it }, placeholder = "Nombre", iconColor = Color(0xFF0095FF))
                    CandyInputField(label = "Categoría", icon = Icons.Default.Category, value = categoria, onValueChange = { categoria = it }, placeholder = "Nombre", iconColor = Color(0xFFFFD600))
                    CandyInputField(label = "Fecha de caducidad", icon = Icons.Default.DateRange, value = fechaCaducidad, onValueChange = { fechaCaducidad = it }, placeholder = "MM/AAAA", iconColor = Color(0xFFFF4081))
                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = { showSuccessDialog = true },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFD600)),
                        modifier = Modifier
                            .align(Alignment.End)
                            .width(120.dp)
                            .height(45.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Guardar", color = Color.Black, fontWeight = FontWeight.Bold)
                    }

                    Spacer(modifier = Modifier.height(24.dp))
                }
            }

            //Alerta de exito
            if (showSuccessDialog){
                Dialog(onDismissRequest = { showSuccessDialog = false }) {
                    Box(
                        modifier = Modifier
                            .size(280.dp)
                            .clip(RoundedCornerShape(24.dp))
                            .background(Color.White)
                            .padding(20.dp),
                        contentAlignment = Alignment.Center
                    ){
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "Guardado",
                                fontSize = 32.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                            Spacer(modifier = Modifier.height(20.dp))

                            Box(
                                modifier = Modifier
                                    .size(80.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFF66BB6A)),
                                contentAlignment = Alignment.Center
                            ){
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(50.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(20.dp))
                            Text(
                                text = "¡Ha sido guardado con éxito!",
                                fontSize = 16.sp,
                                textAlign = TextAlign.Center,
                                color = Color.DarkGray
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CandyInputField(
    label: String,
    icon: ImageVector,
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
                Icon(imageVector = icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(16.dp))
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
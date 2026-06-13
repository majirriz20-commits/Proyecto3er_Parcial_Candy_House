package com.example.candyhouse.Screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.candyhouse.components.CandyBottomBar

@Composable
fun FiltrosScreen(
    onIrAInicio: () -> Unit
) {
    var selectGomitas by remember { mutableStateOf(false) }
    var selectChocolates by remember { mutableStateOf(false) }
    var selectRefrescos by remember { mutableStateOf(false) }
    var selectImportados by remember { mutableStateOf(false) }

    var rangoPrecio by remember { mutableStateOf(1f..2000f) }

    var selectOptimo by remember { mutableStateOf(false) }
    var selectBajo by remember { mutableStateOf(false) }

    var proveedorSeleccionado by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            CandyTopBar(onMenuClick = onIrAInicio)
        },
        bottomBar = {
            CandyBottomBar(
                pantallaActual = "filtros",
                onTabSelected = { pantalla ->
                    if (pantalla == "inicio") {
                        onIrAInicio()
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            HorizontalDivider(color = Color(0xFFE0E0E0), thickness = 1.dp)

            // 1. Categorías
            FiltroDesplegable(titulo = "Categorías") {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(checked = selectGomitas, onCheckedChange = { selectGomitas = it })
                        Text("Gomitas", color = Color.Black)
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(checked = selectChocolates, onCheckedChange = { selectChocolates = it })
                        Text("Chocolates", color = Color.Black)
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(checked = selectRefrescos, onCheckedChange = { selectRefrescos = it })
                        Text("Refrescos", color = Color.Black)
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(checked = selectImportados, onCheckedChange = { selectImportados = it })
                        Text("Importados", color = Color.Black)
                    }
                }
            }

            // 2. Precios
            FiltroDesplegable(titulo = "Precios") {
                Column(modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp)) {
                    Text(
                        text = "Rangos: \$${rangoPrecio.start.toInt()} - \$${rangoPrecio.endInclusive.toInt()}",
                        fontSize = 14.sp,
                        color = Color.DarkGray
                    )
                    Slider(
                        value = rangoPrecio.endInclusive,
                        onValueChange = { rangoPrecio = 1f..it },
                        valueRange = 1f..2000f
                    )
                }
            }

            // 3. Stock
            FiltroDesplegable(titulo = "Stock") {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(checked = selectOptimo, onCheckedChange = { selectOptimo = it })
                        Text("Óptimo", color = Color.Black)
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(checked = selectBajo, onCheckedChange = { selectBajo = it })
                        Text("Bajo", color = Color.Black)
                    }
                }
            }

            // 4. Proveedores
            FiltroDesplegable(titulo = "Proveedores") {
                val proveedores = listOf(
                    "Dulcerías Denny", "Dulcería La Providencia",
                    "Dulcerías y Abarroteras Vázquez", "Dulcerías El Payaso (Produlce)",
                    "Azúcar Dulcerías", "Tienda de la Rosa"
                )
                Column {
                    proveedores.forEach { pro ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { proveedorSeleccionado = pro }
                                .padding(vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = proveedorSeleccionado == pro,
                                onCheckedChange = { if (it) proveedorSeleccionado = pro else proveedorSeleccionado = "" }
                            )
                            Text(text = pro, color = Color.Black, fontSize = 14.sp)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                OutlinedButton(
                    onClick = {
                        selectGomitas = false
                        selectChocolates = false
                        selectRefrescos = false
                        selectImportados = false
                        rangoPrecio = 1f..2000f
                        selectOptimo = false
                        selectBajo = false
                        proveedorSeleccionado = ""
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Gray),
                    border = BorderStroke(1.dp, Color.LightGray),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(text = "Limpiar", fontSize = 16.sp)
                }

                Button(
                    onClick = { onIrAInicio() },
                    modifier = Modifier.weight(1.3f),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF4081)),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(text = "Aplicar Filtros", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun FiltroDesplegable(
    titulo: String,
    contenido: @Composable () -> Unit
) {
    var estaExpandido by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { estaExpandido = !estaExpandido }
                .padding(horizontal = 16.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = titulo,
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Black
            )

            Icon(
                imageVector = if (estaExpandido) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                contentDescription = if (estaExpandido) "Colapsar" else "Expandir",
                tint = Color.DarkGray
            )
        }

        AnimatedVisibility(visible = estaExpandido) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 4.dp)
            ) {
                contenido()
            }
        }

        HorizontalDivider(color = Color(0xFFE0E0E0), thickness = 1.dp)
    }
}
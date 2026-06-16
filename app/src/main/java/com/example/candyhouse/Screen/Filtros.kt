package com.example.candyhouse.Screen

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.candyhouse.components.CandyBottomBar
// IMPORTAMOS EL VIEWMODEL DESDE SU NUEVA RUTA
import com.example.candyhouse.viewmodel.CandyViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FiltrosScreen(
    onIrAInicio: () -> Unit,
    viewModel: CandyViewModel
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Filtros", fontSize = 20.sp, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onIrAInicio) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Regresar")
                    }
                }
            )
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
            HorizontalDivider(color = Color(0xFF000000), thickness = 1.dp)

            // 1. Categorías
            FiltroDesplegable(titulo = "Categorías") {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = viewModel.selectGomitas,
                            onCheckedChange = { viewModel.selectGomitas = it }
                        )
                        Text("Gomitas", color = Color.White)
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = viewModel.selectChocolates,
                            onCheckedChange = { viewModel.selectChocolates = it }
                        )
                        Text("Chocolates", color = Color.White)
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = viewModel.selectBebidas,
                            onCheckedChange = { viewModel.selectBebidas = it }
                        )
                        Text("Bebidas", color = Color.White)
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = viewModel.selectImportados,
                            onCheckedChange = { viewModel.selectImportados = it }
                        )
                        Text("Importados", color = Color.White)
                    }
                }
            }

            // 2. Precios
            FiltroDesplegable(titulo = "Precios") {
                Column(modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp)) {
                    Text(
                        text = "Rangos: \$${viewModel.rangoPrecio.start.toInt()} - \$${viewModel.rangoPrecio.endInclusive.toInt()}",
                        fontSize = 14.sp,
                        color = Color.White,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    RangeSlider(
                        value = viewModel.rangoPrecio,
                        onValueChange = { nuevoRango ->
                            viewModel.rangoPrecio = nuevoRango
                        },
                        valueRange = 1f..2000f,
                        colors = SliderDefaults.colors(
                            activeTrackColor = Color(0xFFFF4081),
                            thumbColor = Color(0xFFFF4081)
                        )
                    )
                }
            }

            // 3. Stock
            FiltroDesplegable(titulo = "Stock") {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = viewModel.selectOptimo,
                            onCheckedChange = { viewModel.selectOptimo = it }
                        )
                        Text("Óptimo", color = Color.White)
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = viewModel.selectBajo,
                            onCheckedChange = { viewModel.selectBajo = it }
                        )
                        Text("Bajo", color = Color.White)
                    }
                }
            }

            // 4. Proveedores
            FiltroDesplegable(titulo = "Proveedores") {
                val proveedores = listOf(
                    "Dulceria Denny",
                    "Dulceria La Providencia",
                    "Dulcerias y Abarroteras Vazquez",
                    "Dulceria El Payaso (Produlce)",
                    "Azucar Dulcerias",
                    "Tienda de la Rosa"
                )
                Column {
                    proveedores.forEach { pro ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { viewModel.proveedorSeleccionado = pro }
                                .padding(vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = viewModel.proveedorSeleccionado == pro,
                                onCheckedChange = { isChecked ->
                                    viewModel.proveedorSeleccionado = if (isChecked) pro else ""
                                }
                            )
                            Text(text = pro, color = Color.White, fontSize = 14.sp)
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
                    onClick = { viewModel.limpiarFiltros() },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
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
                color = Color.White
            )

            Icon(
                imageVector = if (estaExpandido) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                contentDescription = if (estaExpandido) "Colapsar" else "Expandir",
                tint = Color.White
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

        HorizontalDivider(color = Color(0xFF000000), thickness = 1.dp)
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true)
@Composable
fun FiltrosScreenPreview() {
    val viewModelFalso = CandyViewModel()
    FiltrosScreen(
        onIrAInicio = {},
        viewModel = viewModelFalso
    )
}
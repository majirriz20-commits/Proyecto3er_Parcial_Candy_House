package com.example.candyhouse.Screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
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
    onIrAInicio: () -> Unit // Esta función ya la tienes aquí arriba
) {
    Scaffold(
        topBar = {
            // 🌟 SOLUCIÓN: Le pasamos la acción para que si vuelven a picar las 3 barras, regrese al inicio
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
        ) {
            // 1. Línea divisoria inicial debajo del Header
            Divider(color = Color(0xFFE0E0E0), thickness = 1.dp)

            // 2. Desplegable de Categorías
            FiltroDesplegable(titulo = "Categorías") {
                Text(
                    text = "Chocolates, Gomitas, Paletas...",
                    color = Color.Gray,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            // 3. Desplegable de Precios
            FiltroDesplegable(titulo = "Precios") {
                Text(
                    text = "Menos de $50, $50 a $100...",
                    color = Color.Gray,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            // 4. Desplegable de Stock
            FiltroDesplegable(titulo = "Stock") {
                Text(
                    text = "Mostrar solo productos con stock óptimo",
                    color = Color.Gray,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            // 5. Desplegable de Proveedores
            FiltroDesplegable(titulo = "Proveedores") {
                Text(
                    text = "Proveedor A, Proveedor B...",
                    color = Color.Gray,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
        }
    }
}

// Desplegable
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

        Divider(color = Color(0xFFE0E0E0), thickness = 1.dp)
    }
}
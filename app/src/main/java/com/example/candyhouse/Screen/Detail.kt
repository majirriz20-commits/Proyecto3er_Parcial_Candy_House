package com.example.candyhouse.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.candyhouse.R

data class Producto(
    val nombre: String = "Gomitas de oso",
    val codigo: String = "01545789",
    val precio: String = "\$15.00 kg",
    val existencia: String = "150 pz",
    val pasillo: String = "B-04",
    val proveedor: String = "Dulcería Denny",
    val categoria: String = "Gomitas",
    val fechaCaducidad: String = "05/2028"
)

@Composable
fun DetailScreen(
    producto: Producto = Producto(),
    onClose: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Text(
            text = producto.nombre,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp)
        )
        Text(
            text = producto.codigo,
            fontSize = 13.sp,
            color = Color.Gray,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DetailScreenPreview() {
    DetailScreen()
}
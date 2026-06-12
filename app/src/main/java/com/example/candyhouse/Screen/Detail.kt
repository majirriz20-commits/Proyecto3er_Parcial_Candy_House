package com.example.candyhouse.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.draw.clip
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

        // imagn con el boton cerar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(260.dp)
                .background(Color(0xFFE0E0E0))
        ) {
            // Botón X
            IconButton(
                onClick = onClose,
                modifier = Modifier
                    .padding(12.dp)
                    .align(Alignment.TopStart)
                    .size(36.dp)
                    .background(Color.White.copy(alpha = 0.8f), CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Cerrar",
                    tint = Color.DarkGray,
                    modifier = Modifier.size(20.dp)
                )
            }

            //puntitos
            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(Color.Gray))
                Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(Color.DarkGray))
            }
        }
        //nombre y codig
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = producto.nombre,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                text = producto.codigo,
                fontSize = 13.sp,
                color = Color.Gray,
            )
        }
    }

}

    @Preview(showBackground = true)
    @Composable
    fun DetailScreenPreview() {
        DetailScreen()
    }



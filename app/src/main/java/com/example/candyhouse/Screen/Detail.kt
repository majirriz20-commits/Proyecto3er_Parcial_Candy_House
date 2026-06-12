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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.text.style.TextAlign
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


        //emojis info
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            InfoChip(emoji = "🏷️", label = "Precio", value = producto.precio, chipColor = Color(0xFF29B6F6))
            InfoChip(emoji = "📦", label = "Existencia", value = producto.existencia, chipColor = Color(0xFF66BB6A))
            InfoChip(emoji = "📍", label = "Pasillo",    value = producto.pasillo,    chipColor = Color(0xFFFF9800))
        }

        Spacer(modifier = Modifier.height(20.dp))
        HorizontalDivider(color = Color(0xFFEEEEEE))
        Spacer(modifier = Modifier.height(16.dp))

        // Proveedor
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Text("Proveedor", fontSize = 13.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(2.dp))
            Text(producto.proveedor, fontSize = 14.sp, color = Color.DarkGray)
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Categoría
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Text("Categoría", fontSize = 13.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(2.dp))
            Text(producto.categoria, fontSize = 14.sp, color = Color.DarkGray)
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Fecha de caducidad
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Text("Fecha de caducidad", fontSize = 13.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(2.dp))
            Text(producto.fechaCaducidad, fontSize = 14.sp, color = Color.DarkGray)
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
        Text(value,  fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
    }
}

    @Preview(showBackground = true)
    @Composable
    fun DetailScreenPreview() {
        DetailScreen()
    }



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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.foundation.border
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
            .verticalScroll(rememberScrollState())
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
        // ── Nombre, código y botones ───────────────────────────────
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = producto.nombre,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = producto.codigo,
                    fontSize = 13.sp,
                    color = Color.Gray
                )
            }
            // Botón editar
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Editar",
                    tint = Color(0xFFFF3B7B),
                    modifier = Modifier.size(24.dp)
                )
            }
            // Botón eliminar
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Eliminar",
                    tint = Color(0xFFFFC107),
                    modifier = Modifier.size(24.dp)
                )
            }
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
        Spacer(modifier = Modifier.height(24.dp))

        // ── Selector de cantidad ───────────────────────────────────
        var cantidad by remember { mutableStateOf(1) }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Cantidad:", fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                IconButton(
                    onClick = { if (cantidad > 1) cantidad-- },
                    modifier = Modifier
                        .size(32.dp)
                        .border(1.5.dp, Color(0xFFFF3B7B), CircleShape)
                ) {
                    Text("−", fontSize = 18.sp, color = Color(0xFFFF3B7B), fontWeight = FontWeight.Bold)
                }
                Box(
                    modifier = Modifier
                        .border(1.dp, Color.LightGray, RoundedCornerShape(4.dp))
                        .padding(horizontal = 16.dp, vertical = 4.dp)
                ) {
                    Text(text = cantidad.toString(), fontSize = 14.sp)
                }
                IconButton(
                    onClick = { cantidad++ },
                    modifier = Modifier
                        .size(32.dp)
                        .border(1.5.dp, Color(0xFFFF3B7B), CircleShape)
                ) {
                    Text("+", fontSize = 18.sp, color = Color(0xFFFF3B7B), fontWeight = FontWeight.Bold)
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // ── Botones Venta y Pedir ──────────────────────────────────
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = { },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFC107)),
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier.weight(1f).height(48.dp)
            ) {
                Text("🛒  Venta", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 15.sp)
            }
            Button(
                onClick = { },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00D4FF)),
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier.weight(1f).height(48.dp)
            ) {
                Text("⊕  Pedir", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 15.sp)
            }
        }

        Spacer(modifier = Modifier.height(80.dp))
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



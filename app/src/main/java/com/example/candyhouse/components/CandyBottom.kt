package com.example.candyhouse.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CandyBottomBar(
    pantallaActual: String,
    onTabSelected: (String) -> Unit
) {
    NavigationBar(
        containerColor = Color(0xFFFBFBFB),
        contentColor = Color.Black
    ) {
        // 1. Botón de Inicio
        NavigationBarItem(
            selected = pantallaActual == "inicio",
            onClick = { onTabSelected("inicio") },
            icon = { Icon(Icons.Default.Home, contentDescription = "Inicio", modifier = Modifier.size(26.dp)) },
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = Color.Transparent,
                selectedIconColor = Color(0xFFFF4081),
                unselectedIconColor = Color.Gray
            )
        )

        // 2. Botón de Historial
        NavigationBarItem(
            selected = pantallaActual == "historial",
            onClick = { onTabSelected("historial") },
            icon = { Icon(Icons.Default.History, contentDescription = "Historial", modifier = Modifier.size(26.dp)) },
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = Color.Transparent,
                selectedIconColor = Color(0xFF00E5FF),
                unselectedIconColor = Color.Gray
            )
        )

        // 3. Botón Central
        Box(
            modifier = Modifier
                .weight(1f)
                .size(52.dp),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .background(Color(0xFFFF4081), shape = CircleShape)
                    .clickable { onTabSelected("agregar") },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Agregar",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        // 4. Botón de Notificaciones
        NavigationBarItem(
            selected = pantallaActual == "notificaciones",
            onClick = { onTabSelected("notificaciones") },
            icon = { Icon(Icons.Default.Notifications, contentDescription = "Notificaciones", modifier = Modifier.size(26.dp)) },
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = Color.Transparent,
                selectedIconColor = Color(0xFFFFD600),
                unselectedIconColor = Color.Gray
            )
        )

        // 5. Botón de Carrito
        NavigationBarItem(
            // 🌟 CAMBIO AQUÍ: Ahora solo se va a seleccionar si estás textualmente en la pantalla "carrito"
            selected = pantallaActual == "carrito",
            onClick = { onTabSelected("carrito") },
            icon = { Icon(Icons.Default.ShoppingCart, contentDescription = "Carrito", modifier = Modifier.size(26.dp)) },
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = Color.Transparent,
                selectedIconColor = Color(0xFF00E5FF),
                unselectedIconColor = Color.Gray
            )
        )
    }
}
package com.example.candyhouse.Screen



import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.candyhouse.components.CandyBottomBar
import com.example.candyhouse.viewmodel.CandyViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.candyhouse.gestionarSalto

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificacionesScreen(
    viewModel: CandyViewModel = viewModel(),
    navController: NavHostController,
    rutaActual: String
) {
    Scaffold(
        bottomBar = {

            CandyBottomBar(
                pantallaActual = "notificaciones",
                onTabSelected = { destino -> gestionarSalto(navController, destino) }
            )
        },
        containerColor = Color.White
    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp)
        ) {
            //Titulo
            item {
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "Notificaciones",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(24.dp))
            }

           //Bajo stock
            item {
                NotificationHeader(title = "Bajo Stock", icon = "⚠️") // Puedes usar un emoji o Badge interactivo
                Spacer(modifier = Modifier.height(12.dp))
            }
            item {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    NotificationRow(name = "Gomitas de oso", value = "2 kg")
                    NotificationRow(name = "Paletas payaso", value = "25 pz")
                    NotificationRow(name = "Tamarindos", value = "15 pz")
                    NotificationRow(name = "Coca-cola 2L", value = "5 pz")
                }
                Spacer(modifier = Modifier.height(28.dp))
            }

            //Proximo a vencer
            item {
                NotificationHeader(title = "Próximo a vencer", icon = "⏳")
                Spacer(modifier = Modifier.height(12.dp))
            }
            item {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    NotificationRow(name = "Gomitas de oso", value = "07/2026")
                    NotificationRow(name = "Paletas payaso", value = "08/2026")
                    NotificationRow(name = "Tamarindos", value = "09/2026")
                    NotificationRow(name = "Coca-cola 2L", value = "09/2026")
                }
                Spacer(modifier = Modifier.height(28.dp))
            }

            //Entregas
            item {
                NotificationHeader(title = "Entregas", icon = "🚚")
                Spacer(modifier = Modifier.height(12.dp))
            }
            item {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    NotificationRow(name = "Gomitas de oso", value = "08:15 PM")
                    NotificationRow(name = "Paletas payaso", value = "05/06/2026")
                    NotificationRow(name = "Tamarindos", value = "04/06/2026")
                    NotificationRow(name = "Coca-cola 2L", value = "04/06/2026")
                }
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

//icono de cada sección
@Composable
fun NotificationHeader(title: String, icon: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(text = icon, fontSize = 24.sp)
        Text(
            text = title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }
}

//productos y sus estados
@Composable
fun NotificationRow(name: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = name,
            fontSize = 15.sp,
            color = Color.DarkGray
        )
        Text(
            text = value,
            fontSize = 14.sp,
            color = Color.Gray
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun NotificacionesPreview() {
    NotificacionesScreen()
}
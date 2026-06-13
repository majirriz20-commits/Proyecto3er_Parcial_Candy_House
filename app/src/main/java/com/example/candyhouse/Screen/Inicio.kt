package com.example.candyhouse.Screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.candyhouse.components.CandyBottomBar
import com.example.candyhouse.models.Product
import com.example.candyhouse.services.RetrofitClient



@Composable
fun CandyTopBar(onMenuClick: () -> Unit, viewModel: CandyViewModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFE0E0E0))
            .statusBarsPadding()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onMenuClick) {
                Icon(Icons.Default.Menu, "Menú", tint = Color.Black, modifier = Modifier.size(28.dp))
            }

            if (viewModel.buscando) {
                TextField(
                    value = viewModel.textoBusqueda,
                    onValueChange = { viewModel.textoBusqueda = it },
                    placeholder = { Text("Buscar...") },
                    modifier = Modifier.weight(1f).padding(horizontal = 8.dp),
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    )
                )
            } else {
                Spacer(modifier = Modifier.weight(1f))
            }

            IconButton(onClick = {
                if (viewModel.buscando) {
                    viewModel.limpiarFiltros()
                } else {
                    viewModel.buscando = true
                }
            }) {
                Icon(
                    imageVector = if (viewModel.buscando) Icons.Default.Close else Icons.Default.Search,
                    contentDescription = if (viewModel.buscando) "Cerrar" else "Buscar",
                    tint = Color.Black,
                    modifier = Modifier.size(28.dp)
                )
            }
        }

        if (!viewModel.buscando) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Candy House",
                fontSize = 32.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Black,
                modifier = Modifier.padding(start = 12.dp, bottom = 12.dp)
            )
        }
    }
}


@Composable
fun DulceCard(producto: Product) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            AsyncImage(
                model = producto.imageUrl,
                contentDescription = producto.nombre,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .background(Color.White, shape = RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = producto.nombre,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Black
            )

            Text(
                text = "$${String.format("%.2f", producto.precio)}",
                fontSize = 14.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(6.dp))

            val (colorIndicador, textoEstado) = if (producto.estado == "Optimo") {
                Color(0xFF00E5FF) to "Optimo"
            } else {
                Color(0xFFFFD600) to "Bajo"
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .background(colorIndicador, shape = CircleShape)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = textoEstado,
                    fontSize = 12.sp,
                    color = colorIndicador
                )
            }
        }
    }
}

@Composable
fun CandyGridContent(
    productos: List<Product>,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.background(Color.White)
    ) {
        items(productos) { dulce ->
            DulceCard(producto = dulce)
        }
    }
}



@Composable
fun InicioScreen(onIrAFiltros: () -> Unit, viewModel: CandyViewModel) {

    LaunchedEffect(Unit) {
        try {
            val resultado = RetrofitClient.instance.getAllProducts()
            viewModel.listaDesdeApi = resultado
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    Scaffold(
        topBar = {
            CandyTopBar(
                onMenuClick = onIrAFiltros,
                viewModel = viewModel
            )
        },
        bottomBar = {
            CandyBottomBar(
                pantallaActual = "inicio",
                onTabSelected = { pantalla -> /* ... */ }
            )
        }
    ) { innerPadding ->
        if (viewModel.productosFiltradosBusqueda.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(innerPadding)
                    .padding(top = 64.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "No se encontraron resultados",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Gray
                )
            }
        } else {
            CandyGridContent(
                productos = viewModel.productosFiltradosBusqueda,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}




@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true)
@Composable
fun InicioScreenPreview() {
    val viewModelFalso = CandyViewModel()
    InicioScreen(
        onIrAFiltros = {},
        viewModel = viewModelFalso
    )
}
package com.example.candyhouse.Screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
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
import com.example.candyhouse.viewmodel.CandyViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.candyhouse.gestionarSalto

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
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Menú",
                    tint = Color.Black,
                    modifier = Modifier.size(28.dp)
                )
            }

            if (viewModel.buscando) {
                TextField(
                    value = viewModel.textoBusqueda,
                    onValueChange = { viewModel.textoBusqueda = it },
                    placeholder = { Text("Buscar...") },
                    shape = CircleShape,
                    modifier = Modifier.weight(1f).padding(horizontal = 8.dp),
                    singleLine = true,
                    trailingIcon = {
                        IconButton(onClick = {
                            if (viewModel.textoBusqueda.isNotEmpty()) {
                                viewModel.textoBusqueda = ""
                            } else {
                                viewModel.buscando = false
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "Limpiar o cerrar",
                                tint = Color.Gray
                            )
                        }
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )
            } else {
                Spacer(modifier = Modifier.weight(1f))
            }

            IconButton(onClick = {
                if (viewModel.buscando) {
                    viewModel.buscando = false
                } else {
                    viewModel.buscando = true
                }
            }) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Buscar",
                    tint = Color.Black,
                    modifier = Modifier.size(28.dp)
                )
            }
        }

        if (!viewModel.buscando) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Candy House",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(start = 8.dp, bottom = 4.dp)
            )
        }
    }
}

@Composable
fun DulceCard(producto: Product, onClick: () -> Unit = {}) {
    Card(
        onClick = onClick,
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
                Text(text = textoEstado, fontSize = 12.sp, color = colorIndicador)
            }
        }
    }
}

@Composable
fun CandyGridContent(
    productos: List<Product>,
    modifier: Modifier = Modifier,
    onProductoClick: (Product) -> Unit = {}
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.background(Color.White)
    ) {
        items(productos) { dulce ->
            DulceCard(
                producto = dulce,
                onClick = { onProductoClick(dulce) }
            )
        }
    }
}

@Composable
fun InicioScreen(onIrAFiltros: () -> Unit, viewModel: CandyViewModel, navController: NavHostController) {

    LaunchedEffect(Unit) {
        try {
            val resultado = RetrofitClient.apiService.getDulces()
            viewModel.listaDesdeApi = resultado
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    Scaffold(
        topBar = {
            CandyTopBar(onMenuClick = onIrAFiltros, viewModel = viewModel)
        },
        bottomBar = {
            CandyBottomBar(
                pantallaActual = "inicio",
                onTabSelected = { destino -> gestionarSalto(navController, destino) }
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
                    text = "No se encontraron dulces",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Gray
                )
            }
        } else {
            CandyGridContent(
                productos = viewModel.productosFiltradosBusqueda,
                modifier = Modifier.padding(innerPadding),
                onProductoClick = { producto -> navController.navigate("detail/${producto.id}") }
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
        viewModel = viewModelFalso,
        navController = rememberNavController()
    )
}
package com.example.candyhouse.Screen

import android.R.attr.contentDescription
import android.R.attr.tint
import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.candyhouse.models.CartItem
import com.example.candyhouse.services.CartRepository.cartItems
import com.example.candyhouse.ui.theme.BackgroundCandy
import com.example.candyhouse.viewmodel.CandyViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import java.util.Locale
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.candyhouse.models.Product
import com.example.candyhouse.ui.theme.CandyHouseTheme
import models.HistorialViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComprasScreen(
    viewModel: CandyViewModel= viewModel()
){
  val cartItem by viewModel.cartItem.collectAsState()
    var selectedPaymentMethod by remember { mutableStateOf("Efectivo") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Productos Seleccionados",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BackgroundCandy)
            )
        },
        containerColor = BackgroundCandy
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ){
            items(cartItem) { item ->
                CartItemRow(item = item)
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))

                //Calculo del total
                val totalCalculado = viewModel.cartItem.collectAsState().value.sumOf { it.product.precio * it.cantidadSeleccionada }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Total",
                        fontSize = 22.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = "$${String.format(Locale.US, "%.2f", totalCalculado)}",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))

                //Boton de pago
                Button(
                    onClick = { },
                    modifier = Modifier.fillMaxWidth().height(54.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFD600)),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        text = "Pagar (${cartItem.size} productos)",
                        color = Color.Black,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
fun CartItemRow(item: CartItem){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .clip(CircleShape)
                .background(Color.White)
                .border(2.dp, Color.LightGray, CircleShape),
            contentAlignment = Alignment.Center
        ){
            if (item.isChecked){
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
        Spacer(modifier = Modifier.width(12.dp))

        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFFE2E2E2))
        )
        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = item.product.nombre,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
            Text(
                text = "$${item.product.precio}",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(vertical = 4.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                //Selector con los datos de caantidad seleccionada
                Row(
                    modifier = Modifier
                        .border(2.dp, color = Color(0xFFE2E2E2), RoundedCornerShape(12.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = null, tint = Color.Black, modifier = Modifier.size(18.dp))
                    Text(
                        text = item.cantidadSeleccionada.toString(),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier.size(18.dp)
                    )
                }
                Button(
                    onClick = { /* Eliminar */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF4081)),
                    modifier = Modifier.height(32.dp),
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp)
                ) {
                    Text(text = "Eliminar", color = Color.White, fontSize = 12.sp)
                }
            }
        }
    }
}

//PARA PRUEBA

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ShoppingCartPreview() {
    // 1. Creamos un producto de prueba usando el modelo oficial de tu equipo
    val productoPrueba1 = Product(
        id = 1,
        nombre = "Gomitas de osito",
        precio = 15.00,
        estado = "Optimo",
        categoria = "Gomitas",
        imageUrl = "",
        proveedor = "Dulcería Denny",
        existencia = "150 pz",
        pasillo = "B-04",
        fechaCaducidad = "05/2028",
        cantidad = 150.0
    )

    val productoPrueba2 = Product(
        id = 2,
        nombre = "Paleta payaso",
        precio = 25.00,
        estado = "Bajo",
        categoria = "Chocolates",
        imageUrl = "",
        proveedor = "Ricolino",
        existencia = "40 pz",
        pasillo = "A-02",
        fechaCaducidad = "10/2027",
        cantidad = 40.00,
    )

    // 2. Metemos los productos dentro del molde CartItem
    val listaPrueba = listOf(
        CartItem(id = 1, product = productoPrueba1, cantidadSeleccionada = 2.5),
        CartItem(id = 2, product = productoPrueba2, cantidadSeleccionada = 1.0)
    )

    // 3. Dibujamos la estructura de la pantalla pasándole la lista estática
    // NOTA: Si modificaste tu ShoppingCartScreen para que acepte una lista por defecto, se la pasas aquí.
    // Si tu Scaffold está limpio, puedes llamar a tu componente contenedor directo:
    CandyHouseTheme {
        // Aquí llamamos al contenedor de tu pantalla.
        // Si te marca error por el ViewModel, puedes rodear el diseño de tu LazyColumn aquí adentro para verlo:
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = BackgroundCandy
        ) {
            // Recreación del contenido para el Preview
            LazyColumn(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(listaPrueba) { item ->
                    CartItemRow(item = item)
                }
            }
        }
    }
}
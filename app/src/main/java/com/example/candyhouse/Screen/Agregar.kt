package com.example.candyhouse.Screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.candyhouse.viewmodel.CandyViewModel

@Composable
fun AddProducts(
    onClose: () -> Unit = {},
    viewModel: CandyViewModel = viewModel()
){
    // Estados para cada campo de texto
    var titulo by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var existencia by remember { mutableStateOf("") }
    var pasillo by remember { mutableStateOf("") }
    var proveedor by remember { mutableStateOf("") }
    var categoria by remember { mutableStateOf("") }
    var fechaCaducidad by remember { mutableStateOf("") }

    //Anuncio de Guardado con exito

}
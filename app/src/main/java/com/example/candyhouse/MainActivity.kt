package com.example.candyhouse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.candyhouse.Screen.NavGraph
import com.example.candyhouse.ui.theme.CandyHouseTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CandyHouseTheme {
                val navController = rememberNavController()
                NavGraph(navController = navController)
            }
        }
    }
}
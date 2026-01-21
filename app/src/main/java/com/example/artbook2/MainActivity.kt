package com.example.artbook2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.artbook2.ui.theme.ArtBook2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ArtBook2Theme {

                val navController = rememberNavController()

                val viewModel : ArtViewModel = viewModel()

                NavHost(navController = navController, startDestination = "home", builder = {
                    composable("home"){
                        HomeScreen(navController = navController, viewModel = viewModel)
                    }
                    composable("detail"){
                        ArtScreen(navController = navController, viewModel = viewModel)
                    }
                })
            }
        }
    }
}

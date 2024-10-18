package com.example.spacex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.spacex.presentation.components.BottomNavigationBar
import com.example.spacex.presentation.screens.HomeScreen
import com.example.spacex.presentation.screens.LaunchesScreen
import com.example.spacex.presentation.screens.Logo
import com.example.spacex.presentation.screens.RocketListScreen
import com.example.spacex.presentation.screens.Screen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                Surface {
                    AppContent()
                }
            }
        }
    }

    @Composable
    fun AppContent() {
        val navController = rememberNavController()
        Scaffold(
            modifier = Modifier.background(Color(40,40,40)),
            topBar = { Logo() },
            bottomBar = { BottomNavigationBar(navController) }
        ) { paddingValues -> Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(40, 40, 40)) // Set your desired background color here
        ) {
            Column(modifier = Modifier.padding(paddingValues)) {
                NavHost(navController, startDestination = Screen.Home.route) {
                    composable(Screen.Home.route) { HomeScreen() }
                    composable(Screen.Rockets.route) { RocketListScreen() }
                    composable(Screen.Launches.route) { LaunchesScreen() }
                }
            }
        }
        }
    }
}


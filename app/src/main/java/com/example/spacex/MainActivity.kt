package com.example.spacex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.spacex.presentation.components.BottomNavigationBar
import com.example.spacex.presentation.screens.HomeScreen
import com.example.spacex.presentation.screens.LaunchesScreen
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
    fun Logo(){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .padding(start = 15.dp, top = 40.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start)
        {
            Image(
                painter = painterResource(id = R.drawable.spacex),
                contentDescription = "SpaceX",
                modifier = Modifier.size(200.dp)
            )
        }
    }


@Composable
    fun AppContent() {
        val navController = rememberNavController()

        Scaffold(
            modifier = Modifier
                .background(Color(40, 40, 40)), // Background color for the whole app
            topBar = { Logo() },
            bottomBar = { BottomNavigationBar(navController) }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(40, 40, 40)) // Background color for the Box
                    .padding(paddingValues) // Apply padding to avoid system UI overlap
            ) {
                // Main content area
                NavHost(navController, startDestination = Screen.Home.route) {
                    composable(Screen.Home.route) { HomeScreen() }
                    composable(Screen.Rockets.route) { RocketListScreen() }
                    composable(Screen.Launches.route) { LaunchesScreen() }
                }
            }
        }
    }
}


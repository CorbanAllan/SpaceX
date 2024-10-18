package com.example.spacex.presentation.components
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Rocket
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.RocketLaunch
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.spacex.presentation.screens.Screen

@Composable
fun BottomNavigationBar(navController: NavController) {
    NavigationBar(
        containerColor = Color(60, 60, 60),
        tonalElevation = 8.dp,
        contentColor = Color.White,
        modifier = Modifier.height(80.dp)
            .padding(6.dp) // Optional: Add padding around the nav bar
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
    ) {
        val navBackStackEntry = navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry.value?.destination?.route

        NavigationBarItem(
            modifier = Modifier.padding(top = 25.dp),
            icon = { Icon(imageVector = Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Home", fontSize = 14.sp) },
            selected = currentRoute == Screen.Home.route,
            onClick = { navController.navigate(Screen.Home.route) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White, // Icon color when selected
                selectedTextColor = Color.White, // Text color when selected
                unselectedIconColor = Color.White.copy(alpha = 0.6f), // Icon color when unselected
                unselectedTextColor = Color.White.copy(alpha = 0.6f),  // Text color when unselected
                indicatorColor = Color(60, 60, 60)
            )
        )
        NavigationBarItem(
            modifier = Modifier.padding(top = 25.dp),
            icon = {
                Icon(
                    imageVector = Icons.Default.Rocket,
                    contentDescription = "Rockets"
                )
            },
            label = { Text("Rockets", fontSize = 14.sp) },
            selected = currentRoute == Screen.Rockets.route,
            onClick = { navController.navigate(Screen.Rockets.route) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White, // Icon color when selected
                selectedTextColor = Color.White, // Text color when selected
                unselectedIconColor = Color.White.copy(alpha = 0.6f), // Icon color when unselected
                unselectedTextColor = Color.White.copy(alpha = 0.6f),
                indicatorColor = Color(60, 60, 60)// Text color when unselected
            )
        )
        NavigationBarItem(
            modifier = Modifier.padding(top = 25.dp),
            icon = {
                Icon(
                    imageVector = Icons.Default.RocketLaunch,
                    contentDescription = "Launches"
                )
            },
            label = { Text("Launches", fontSize = 14.sp) },
            selected = currentRoute == Screen.Launches.route,
            onClick = { navController.navigate(Screen.Launches.route) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White, // Icon color when selected
                selectedTextColor = Color.White, // Text color when selected
                unselectedIconColor = Color.White.copy(alpha = 0.6f), // Icon color when unselected
                unselectedTextColor = Color.White.copy(alpha = 0.6f),
                indicatorColor = Color(60, 60, 60)// Text color when unselected
            )
        )
    }
}

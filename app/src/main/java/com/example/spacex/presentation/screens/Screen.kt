package com.example.spacex.presentation.screens

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Rockets : Screen("rockets")
    object Launches : Screen("launches")
}
package com.example.spacex
import android.content.Context

fun loadJsonFromAssets(context: Context, filename: String): String {
    return context.assets.open(filename).bufferedReader().use { it.readText() }
}
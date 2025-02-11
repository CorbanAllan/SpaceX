package com.example.spacex.data.model

import com.google.gson.annotations.SerializedName


data class Rocket(
    val id: String,            // Unique identifier for the rocket
    val name: String,          // Name of the rocket
    val description: String,   // Description of the rocket
    val height: Height,        // Height of the rocket (nested class)
    @SerializedName("flickr_images") val flickrImages: List<String> // List of image URLs, assuming SpaceX API provides this
)

data class Height(val meters: Double)


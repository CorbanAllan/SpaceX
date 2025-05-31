package com.example.spacex.data.model

import com.google.gson.annotations.SerializedName

data class RocketResponse(val results: List<LauncherConfig>)

data class LauncherConfig(
    val id: Int,
    val name: String,
    val description: String?,
    @SerializedName("image_url") val imageUrl: String?,
    @SerializedName("variant")
    val variant: String?,
    @SerializedName("successful_launches")
    val successfulLaunches: Int?,
    @SerializedName("failed_launches")
    val failedLaunches: Int?,
    val manufacturer: Manufacturer
)

data class Manufacturer(
    val name: String
)

package com.example.spacex.data.model

import com.google.gson.annotations.SerializedName

data class LaunchResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<Launch>
)

data class Launch(
    val id: String,
    val name: String,
    @SerializedName("net") val dateUtc: String,
    val status: LaunchStatus,
    val rocket: RocketInfo,
    val mission: Mission?,
    val pad: Pad?,
    val image: String?,
    @SerializedName("webcast_live") val webcastLive: Boolean,
    @SerializedName("vidURLs") val videoUrls: List<String>?
)

data class LaunchStatus(
    val id: Int,
    val name: String
)

data class RocketInfo(
    val id: Int,
    val configuration: RocketConfiguration
)

data class RocketConfiguration(
    val id: Int,
    val name: String,
)

data class Mission(
    val description: String?
)

data class Pad(
    val name: String?,
    val location: Location?
)

data class Location(
    val name: String?
)


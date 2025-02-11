package com.example.spacex.data.model

import com.google.gson.annotations.SerializedName

data class Launch(
    val name: String,
    val date_utc: String,
    val success: Boolean?,
    val details: String?,
    val rocket: String,
    val links: Links,
    @SerializedName("flickr") val flickr: List<String>
)

data class Links(
    val patch: Patch
)

data class Patch(
    val small: String?,
    val large: String?
)



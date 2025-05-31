package com.example.spacex.data.model

data class UpcomingLaunchResponse(
    val results: List<UpcomingLaunch>
)

data class UpcomingLaunch(
    val net: String,
    val rocket: UpcomingRocket,
    val pad: UpcomingLaunchPad,
    val image: String?
)

data class UpcomingRocket(
    val configuration: UpcomingLaunchConfiguration
)

data class UpcomingLaunchConfiguration(
    val name: String
)

data class UpcomingLaunchPad(
    val location: UpcomingLaunchLocation
)

data class UpcomingLaunchLocation(
    val name: String
)
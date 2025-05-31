package com.example.spacex.data.network
import com.example.spacex.data.model.LaunchResponse
import retrofit2.http.GET
import retrofit2.http.Query
import com.example.spacex.data.model.UpcomingLaunchResponse
import com.example.spacex.data.model.RocketResponse
import retrofit2.Response

interface SpaceDevsApi {
    @GET("launch/upcoming/")
    suspend fun getUpcomingSpaceXLaunch(
        @Query("lsp__name") lspName: String = "SpaceX",
        @Query("limit") limit: Int = 1,
        @Query("ordering") ordering: String = "net",
    ): UpcomingLaunchResponse
    @GET("config/launcher/")
    suspend fun getRockets(
        @Query("manufacturer__name") manufacturerName: String = "SpaceX",
        @Query("mode") mode: String = "detailed"
    ): RocketResponse
    @GET("launch/")
    suspend fun getSpaceXLaunches(
        @Query("limit") limit: Int = 100,
        @Query("offset") offset: Int = 0,
        @Query("ordering") ordering: String = "-net",
        @Query("lsp__name") lspName: String = "SpaceX",
        @Query("upcoming") upcoming: Boolean? = null // Add this
    ): Response<LaunchResponse>


}
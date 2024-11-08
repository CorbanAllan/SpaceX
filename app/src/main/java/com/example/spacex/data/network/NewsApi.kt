package com.example.spacex.data.network
import com.example.spacex.data.model.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("v2/everything")
    suspend fun getSpaceXNews(
        @Query("q") query: String = "SpaceX",
        @Query("apiKey") apiKey: String
    ): NewsResponse
}
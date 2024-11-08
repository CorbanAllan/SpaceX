package com.example.spacex.data.network
import com.example.spacex.data.model.Launch
import com.example.spacex.data.model.Rocket
import retrofit2.http.GET
import retrofit2.http.Query

interface SpaceXApi {
    @GET("launches")
    suspend fun getLaunches(): List<Launch>
    @GET("rockets")
    suspend fun getRockets(): List<Rocket>
}
package com.example.spacex.data.network
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object SpaceDevsClient {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://ll.thespacedevs.com/2.2.0/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api: SpaceDevsApi = retrofit.create(SpaceDevsApi::class.java)
}
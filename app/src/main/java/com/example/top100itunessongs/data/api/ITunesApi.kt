package com.example.top100itunessongs.data.api

import com.example.top100itunessongs.data.model.SongsResponse
import retrofit2.Response
import retrofit2.http.GET


interface ITunesApi {

    @GET("us/rss/topalbums/limit=100/json")
    suspend fun getTop100Songs(): Response<SongsResponse>
}
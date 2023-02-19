package com.example.top100itunessongs.data.repository

import com.example.top100itunessongs.data.model.SongUiModel
import com.example.top100itunessongs.data.model.SongsResponse
import retrofit2.Response

interface SongsListRepository {

    suspend fun loadSongsFromWeb(): Response<SongsResponse>

    suspend fun getSongsFromLocalDataBase(): List<SongUiModel>

    suspend fun updateSongsInLocalDb(songs: List<SongUiModel>)
}
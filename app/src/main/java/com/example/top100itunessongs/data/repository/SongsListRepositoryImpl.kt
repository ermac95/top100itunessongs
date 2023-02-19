package com.example.top100itunessongs.data.repository

import com.example.top100itunessongs.data.api.ITunesApi
import com.example.top100itunessongs.data.localdatabase.SongsDao
import com.example.top100itunessongs.data.model.SongUiModel
import com.example.top100itunessongs.data.model.SongsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class SongsListRepositoryImpl (
    private val netWorkApi: ITunesApi,
    private val localDataSource: SongsDao
): SongsListRepository {

    override suspend fun loadSongsFromWeb(): Response<SongsResponse>{
        return netWorkApi.getTop100Songs()
    }

    override suspend fun getSongsFromLocalDataBase(): List<SongUiModel> = withContext(Dispatchers.IO) {
        localDataSource.getAllSongs()
    }

    override suspend fun updateSongsInLocalDb(songs: List<SongUiModel>) {
        localDataSource.insertAllSongs(songs)
    }
}
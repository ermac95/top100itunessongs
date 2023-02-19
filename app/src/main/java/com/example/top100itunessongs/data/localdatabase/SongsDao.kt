package com.example.top100itunessongs.data.localdatabase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.top100itunessongs.data.model.SongUiModel

@Dao
interface SongsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllSongs(songsList: List<SongUiModel>)

    @Query("SELECT * FROM SongUiModel")
    suspend fun getAllSongs(): List<SongUiModel>
}
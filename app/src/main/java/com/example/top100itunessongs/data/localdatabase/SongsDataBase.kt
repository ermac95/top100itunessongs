package com.example.top100itunessongs.data.localdatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.top100itunessongs.data.model.SongUiModel

@Database(entities = [SongUiModel::class], version = 2)
abstract class SongsDataBase: RoomDatabase() {

    abstract fun getSongsDao(): SongsDao

    companion object {
        fun getInstance(context: Context): SongsDataBase{
            return Room.databaseBuilder(
                    context,
                    SongsDataBase::class.java,
                    "songs_database"
            )
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}
package com.example.top100itunessongs.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SongUiModel(
    @PrimaryKey
    val id: String,
    val title: String,
    val artist: String,
    val price: String,
    val imageUrl: String,
    val genre: String,
    val shareUrl: String,
)
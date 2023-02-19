package com.example.top100itunessongs.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.top100itunessongs.data.repository.SongsListRepository
import javax.inject.Inject


@Suppress("UNCHECKED_CAST")
class AppViewModelFactory @Inject constructor(
    private val songsListRepo: SongsListRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = when(modelClass) {
        SongsViewModel::class.java -> SongsViewModel(songsListRepo) as T
        else -> throw IllegalArgumentException("wrong dependencies")
    }
}
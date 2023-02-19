package com.example.top100itunessongs.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.top100itunessongs.data.model.SongUiModel
import com.example.top100itunessongs.data.model.convertToUiModel
import com.example.top100itunessongs.data.repository.SongsListRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.Collections.emptyList
import javax.inject.Inject

class SongsViewModel @Inject constructor(
    val songsListRepository: SongsListRepository
): ViewModel() {

    private var songsItemsList: List<SongUiModel> = emptyList()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
        Log.e(this::class.java.simpleName, "CoroutineExceptionHandler:$throwable")
    }

    private val _songsList = MutableStateFlow(emptyList<SongUiModel>())
    val songsList: StateFlow<List<SongUiModel>> = _songsList

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage

    init {
        loadTop100SongsList()
    }

    private fun loadTop100SongsList() {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler){
            val songsCacheData = songsListRepository.getSongsFromLocalDataBase()
            if (songsCacheData.isNotEmpty()) {
                songsItemsList = songsCacheData
                _songsList.value = songsCacheData
            } else {
                val response = songsListRepository.loadSongsFromWeb()
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        val songsData = responseBody?.feed?.songsResponse
                        if (songsData != null) {
                            val songsList = songsData.convertToUiModel()
                            songsItemsList = songsList
                            songsListRepository.updateSongsInLocalDb(songsList)
                            _songsList.value = songsList
                        } else {
                            onError("Unfortunately we found no songs for you")
                        }
                    } else {
                        onError("Something went wrong when trying to load songs from server")
                    }
                }
            }
        }
    }

    private fun onError(message: String) {
        _errorMessage.value = message
    }

    fun searchSongsByQuery(query: String) {
        if (query.length > 3) {
            val songsListMatchingTitle = songsItemsList.filter { it.title.lowercase().contains(query) }
            val songsListMatchingArtist = songsItemsList.filter { it.artist.lowercase().contains(query) }
            val searchResult = songsListMatchingArtist + songsListMatchingTitle
            _songsList.value = searchResult
        } else {
            _songsList.value = songsItemsList
        }
    }
}


package com.example.top100itunessongs.di

import com.example.top100itunessongs.data.api.ITunesApi
import com.example.top100itunessongs.data.localdatabase.SongsDao
import com.example.top100itunessongs.data.repository.SongsListRepository
import com.example.top100itunessongs.data.repository.SongsListRepositoryImpl
import com.example.top100itunessongs.presentation.viewmodels.AppViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideViewModelFactory(
        songsListRepository: SongsListRepository
    ): AppViewModelFactory {
        return AppViewModelFactory(songsListRepository)
    }

    @Provides
    @Singleton
    fun provideSongsListRepository(
        iTunesApi: ITunesApi,
        localDataSource: SongsDao
    ): SongsListRepository {
        return SongsListRepositoryImpl(
            iTunesApi,
            localDataSource
        )
    }
}
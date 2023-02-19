package com.example.top100itunessongs.di

import android.content.Context
import com.example.top100itunessongs.data.localdatabase.SongsDao
import com.example.top100itunessongs.data.localdatabase.SongsDataBase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class DataBaseModule {

    @Provides
    @Singleton
    fun provideAppDataBase(context: Context): SongsDataBase {
        return SongsDataBase.getInstance(context)
    }

    @Provides
    @Singleton
    fun provideSongsDao(appDataBase: SongsDataBase): SongsDao{
        return appDataBase.getSongsDao()
    }
}
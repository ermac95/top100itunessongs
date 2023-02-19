package com.example.top100itunessongs.di

import android.content.Context
import com.example.top100itunessongs.presentation.ui.SongsListFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [AppModule::class, NetworkModule::class, DataBaseModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory{
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(frag: SongsListFragment)
}
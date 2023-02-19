package com.example.top100itunessongs

import android.app.Application
import com.example.top100itunessongs.di.AppComponent
import com.example.top100itunessongs.di.DaggerAppComponent

class MyApp: Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        initDagger()
    }

    private fun initDagger() {
        appComponent = DaggerAppComponent.factory()
            .create(applicationContext)
    }
}
package com.example.freeweights

import android.app.Application
import com.example.freeweights.data.AppContainer
import com.example.freeweights.data.AppDataContainer


class MyApp : Application() {
    lateinit var appContainer: AppContainer

    //The creates a application "context" that is a requirement for many of the database stuff
    override fun onCreate() {
        super.onCreate()
        appContainer = AppDataContainer(this)
    }

}
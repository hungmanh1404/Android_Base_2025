package com.example.android_base_2025

import android.app.Application
import android.util.Log

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        initLogger()
        initDatabase()
        initApiClient()
    }

    private fun initLogger() {
        Log.d("AppInit", "Logger initialized ✅")
    }

    private fun initDatabase() {

        Log.d("AppInit", "Database initialized ✅")
    }

    private fun initApiClient() {
        Log.d("AppInit", "ApiClient initialized ✅")
    }
}

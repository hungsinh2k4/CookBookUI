package com.example.androidcookbook

import android.app.Application
import com.example.androidcookbook.data.AppContainer
import com.example.androidcookbook.data.DefaultAppContainer

class CookbookApplication: Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}
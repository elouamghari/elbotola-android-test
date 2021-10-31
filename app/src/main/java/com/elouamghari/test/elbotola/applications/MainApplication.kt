package com.elouamghari.test.elbotola.applications

import android.app.Application
import android.content.Context

class MainApplication : Application() {

    companion object{
        private lateinit var instance: MainApplication
        val context: Context by lazy {
            instance.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

}
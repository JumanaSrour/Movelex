package com.example.newmovlex.utils

import android.app.Application


class MovlexApp : Application() {
    companion object {
        lateinit var instance: Application
        @JvmName("getInstance1")
        public fun getInstance(): Application {
            return instance
        }
    }
    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}
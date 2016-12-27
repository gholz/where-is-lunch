package com.guilhermeholz.whereislunch

import android.app.Application
import com.guilhermeholz.whereislunch.di.AndroidModule
import com.guilhermeholz.whereislunch.di.AppComponent
import com.guilhermeholz.whereislunch.di.DaggerAppComponent
import com.guilhermeholz.whereislunch.di.NetworkModule

class MainApp : Application() {

    companion object {
        lateinit var component: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        component = DaggerAppComponent.builder()
                .androidModule(AndroidModule(this))
                .networkModule(NetworkModule())
                .build()
    }
}
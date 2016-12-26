package com.guilhermeholz.whereislunch

import android.app.Application
import com.guilhermeholz.whereislunch.di.AndroidModule
import com.guilhermeholz.whereislunch.di.AppComponent
import com.guilhermeholz.whereislunch.di.DaggerAppComponent
import com.guilhermeholz.whereislunch.di.NetworkModule

class MainApp : Application() {

    companion object {
        val component: AppComponent by lazy {
            DaggerAppComponent.builder()
                    .androidModule(AndroidModule())
                    .networkModule(NetworkModule())
                    .build()
        }
    }
}
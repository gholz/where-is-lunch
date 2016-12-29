package com.guilhermeholz.whereislunch

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import com.guilhermeholz.whereislunch.android.receivers.ChampionAlarmReceiver
import com.guilhermeholz.whereislunch.di.AndroidModule
import com.guilhermeholz.whereislunch.di.AppComponent
import com.guilhermeholz.whereislunch.di.DaggerAppComponent
import com.guilhermeholz.whereislunch.di.NetworkModule
import com.jakewharton.threetenabp.AndroidThreeTen
import java.util.*
import javax.inject.Inject

class MainApp : Application() {

    companion object {
        lateinit var component: AppComponent
    }

    @Inject
    lateinit var preferences:SharedPreferences

    override fun onCreate() {
        super.onCreate()
        component = DaggerAppComponent.builder()
                .androidModule(AndroidModule(this))
                .networkModule(NetworkModule())
                .build()
        init()
    }

    private fun init() {
        component.inject(this)
        AndroidThreeTen.init(this)
        scheduleAlarm()
    }

    private fun scheduleAlarm() {
        val intent = Intent(this, ChampionAlarmReceiver::class.java)
        val pending = PendingIntent.getBroadcast(this, 0, intent, 0)

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        calendar.set(Calendar.HOUR_OF_DAY, 12)
        calendar.set(Calendar.MINUTE, 59)

        val manager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, AlarmManager.INTERVAL_DAY, pending)
    }
}
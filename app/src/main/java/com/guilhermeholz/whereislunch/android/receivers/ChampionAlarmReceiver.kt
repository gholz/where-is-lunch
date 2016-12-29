package com.guilhermeholz.whereislunch.android.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.guilhermeholz.whereislunch.android.services.GetChampionService

class ChampionAlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        context?.startService(Intent(context, GetChampionService::class.java))
    }
}
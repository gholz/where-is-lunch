package com.guilhermeholz.whereislunch.android.services

import android.app.IntentService
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.location.Location
import android.support.v7.app.NotificationCompat
import com.guilhermeholz.whereislunch.MainApp
import com.guilhermeholz.whereislunch.R
import com.guilhermeholz.whereislunch.domain.RestaurantsRepository
import com.guilhermeholz.whereislunch.domain.model.Restaurant
import com.guilhermeholz.whereislunch.domain.model.RestaurantDetail
import com.guilhermeholz.whereislunch.ui.activities.RestaurantDetailActivity
import com.guilhermeholz.whereislunch.ui.activities.SearchActivity
import com.guilhermeholz.whereislunch.utils.fill
import com.guilhermeholz.whereislunch.utils.logError
import rx.Observable
import javax.inject.Inject

class GetChampionService : IntentService("ChampionService") {

    @Inject
    lateinit var repository: RestaurantsRepository

    @Inject
    lateinit var preferences: SharedPreferences

    init {
        MainApp.component.inject(this)
    }

    override fun onHandleIntent(intent: Intent?) {
        repository.getWinner()
                .subscribe({
                    sendNotification(it)
                }, {
                    logError(it)
                })
    }

    private fun sendNotification(restaurant: RestaurantDetail?) {
        restaurant?.let {
            val intent = Intent(this, SearchActivity::class.java)
            intent.putExtra(RestaurantDetailActivity.idExtraKey, it.id)

            val pending = PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            val builder = NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(getString(R.string.label_notification_title))
                    .setContentText(it.name)
                    .setColor(Color.YELLOW)
                    .setDefaults(Notification.DEFAULT_SOUND and Notification.DEFAULT_LIGHTS)
                    .setVibrate(longArrayOf(200, 200, 200))
                    .setTicker(it.name)
                    .setAutoCancel(true)
                    .setContentIntent(pending)

            val manager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.notify(1, builder.build())
        }
    }
}
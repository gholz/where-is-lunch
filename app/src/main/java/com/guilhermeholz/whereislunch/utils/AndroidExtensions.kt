package com.guilhermeholz.whereislunch.utils

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log

fun AppCompatActivity.hasLocationPermission() = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
fun AppCompatActivity.askForLocationPermission() = ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 100)
fun Any.logError(throwable: Throwable) = Log.e(this.javaClass.simpleName, throwable.message, throwable)
fun Location.isNotTheSameAs(location: Location?, tolerance: Int = 10) = location == null || this.distanceTo(location) > tolerance
fun Location.toSimpleString() = "${this.latitude},${this.longitude}"
fun Location.fill(coordinates:String) {
    val values = coordinates.split(",")
    this.latitude = values[0].toDouble()
    this.longitude = values[1].toDouble()
}
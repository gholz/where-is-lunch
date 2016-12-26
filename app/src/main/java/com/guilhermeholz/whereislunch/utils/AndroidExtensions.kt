package com.guilhermeholz.whereislunch.utils

import android.Manifest
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log

fun AppCompatActivity.hasLocationPermission() = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
fun AppCompatActivity.askForLocationPermission() = ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 100)
fun AppCompatActivity.log(message:String) = Log.i(this.javaClass.simpleName, message)
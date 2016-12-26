package com.guilhermeholz.whereislunch.ui.activities

import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.guilhermeholz.whereislunch.utils.askForLocationPermission
import com.guilhermeholz.whereislunch.utils.hasLocationPermission

abstract class LocationActivity : AppCompatActivity(), GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    val googleApi: GoogleApiClient by lazy {
        GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build()
    }

    val locationRequest: LocationRequest by lazy {
        LocationRequest().setNumUpdates(1).setInterval(100)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!hasLocationPermission()) {
            askForLocationPermission()
        }
    }

    override fun onStart() {
        if (hasLocationPermission()) {
            googleApi.connect()
        }
        super.onStart()
    }

    override fun onStop() {
        if (hasLocationPermission()) {
            googleApi.disconnect()
        }
        super.onStop()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            googleApi.connect()
        }
    }

    override fun onConnected(hint: Bundle?) {
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApi, locationRequest, this)
    }

    override fun onConnectionSuspended(cause: Int) {

    }

    override fun onConnectionFailed(result: ConnectionResult) {

    }
}
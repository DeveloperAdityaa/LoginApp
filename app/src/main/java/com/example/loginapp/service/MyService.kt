package com.example.loginapp.service

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import com.example.loginapp.database.LocationDatabase
import com.example.loginapp.model.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.security.Provider

class MyService : Service() {
    val TAG = "Service testing"
    lateinit var database: LocationDatabase
    lateinit var loction: FusedLocationProviderClient


    override fun onBind(p0: Intent?): IBinder? = null
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        if (intent != null) {
            getLocation()
        }


        return START_STICKY
    }

    @SuppressLint("MissingPermission")
    private fun getLocation() {
        loction = LocationServices.getFusedLocationProviderClient(this)
        val task = loction.lastLocation
        task.addOnSuccessListener {
            if (it != null) {
                val latitude = it.latitude
                val longitude = it.longitude
                Toast.makeText(applicationContext, "Your location fetched ${latitude} ${longitude}", Toast.LENGTH_SHORT)
                    .show()
                storeLocation(latitude, longitude)
            }
        }

    }
    private fun storeLocation(lat : Double, longi : Double) {
        GlobalScope.launch {
            database = LocationDatabase.getDatabase(applicationContext)
            database.locationDao().insertLocation(Location(1, lat, longi))
        }
    }

}
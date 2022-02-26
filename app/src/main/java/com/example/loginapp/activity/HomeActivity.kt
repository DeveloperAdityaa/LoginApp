package com.example.loginapp.activity

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.loginapp.R
import com.example.loginapp.database.LocationDatabase
import com.example.loginapp.model.Location
import com.example.loginapp.service.MyService
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity() {


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val data = intent.getStringExtra("data")
        val btnStart = findViewById<Button>(R.id.btnStart)
        val txtGretting = findViewById<TextView>(R.id.txtGretting)

        txtGretting.text = "Welcome "+ intent.getStringExtra("name")

        showNotification()

        btnStart.setOnClickListener {

            Intent(this, MyService::class.java).also {
                checkForPermission()
                it.putExtra("data", data)
                startService(it)
            }

        }
    }



    private fun checkForPermission() {


        if (ActivityCompat.checkSelfPermission(
                this,
                (android.Manifest.permission.ACCESS_FINE_LOCATION)
            )
            != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED
        ) {

            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                199
            )

        }
    }
    private fun showNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel =
                NotificationChannel(
                    "MyNotification",
                    "MyNotification",
                    NotificationManager.IMPORTANCE_DEFAULT
                )


            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(notificationChannel)
        }

        val builder = NotificationCompat.Builder(this, "MyNotification")
            .setContentTitle("Login app")
            .setContentText("Location sync start")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setAutoCancel(true)

        val notificationManager = NotificationManagerCompat.from(this)
        notificationManager.notify(999, builder.build())
    }
}
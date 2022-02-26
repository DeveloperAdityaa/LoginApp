package com.example.loginapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "location")
data class Location(

    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val latitude: Double,
    val logitude: Double
)

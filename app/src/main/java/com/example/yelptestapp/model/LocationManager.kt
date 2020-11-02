package com.example.yelptestapp.model

import android.location.Location

// Simple singleton to cache last location
object LocationManager {

    var current: Location? = null
        set(value) = synchronized(LocationManager::class.java) {
            field = value
        }
}
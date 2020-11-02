package com.example.yelptestapp

import android.location.Location
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.BusinessSearchQuery
import com.example.yelptestapp.model.LocationManager
import kotlin.math.roundToInt

@BindingAdapter("double_as_text")
fun TextView.setTextOfDouble(double: Double?) {
    this.text = double.toString()
}

@BindingAdapter("distance_text")
fun TextView.setDistanceText(coordinates: BusinessSearchQuery.Coordinates?) {
    val currentLocation = LocationManager.current
    if (currentLocation != null
        && coordinates != null
        && coordinates.latitude != null
        && coordinates.longitude != null
    ) {
        val result = floatArrayOf(0.0F, 0.0F, 0.0F)
        Location.distanceBetween(
            currentLocation.latitude, currentLocation.longitude,
            coordinates.latitude, coordinates.longitude,
            result
        )
        text = context.getString(R.string.distance, result[0].roundToInt())
    }
}

@BindingAdapter("image_url")
fun ImageView.loadImageUrlWithGlide(imageUrl: String?) {
    imageUrl?.let {
        // Convert to URI
        val uri = it
            .toUri()
            .buildUpon()
            .scheme("https")
            .build()

        // Load
        Glide.with(this.context)
            .load(uri)
            .into(this)
    }
}

package com.example.tailor.util

import android.content.Context
import android.location.Geocoder
import java.util.*

fun Context.getCityName(lat:Double, long:Double): String{

    val geoCoder = Geocoder(this, Locale.getDefault())
    val address = geoCoder.getFromLocation(lat,long,1)
    return address[0].locality
}
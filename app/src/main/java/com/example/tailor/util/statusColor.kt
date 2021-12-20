package com.example.tailor.util

import android.app.Activity
import android.os.Build
import android.view.View
import android.view.WindowManager

enum class statusIconColorType {
    Dark, Light
}
fun Activity.setStatusBarColor(Color:Int,iconColorType: statusIconColorType = statusIconColorType.Light){
    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
        this.window.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
            statusBarColor = Color
            decorView.systemUiVisibility = when(iconColorType){
                statusIconColorType.Dark -> View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                statusIconColorType.Light -> 0
            }
        }
    }else{
        this.window.statusBarColor = Color
    }
}
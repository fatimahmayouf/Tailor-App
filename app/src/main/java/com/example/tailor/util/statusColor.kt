package com.example.tailor.util

import android.app.Activity
import android.os.Build
import android.view.View
import android.view.WindowManager

enum class statusIconColorType {
    DARK, LIGHT
}
fun Activity.setStatusBarColor(Color:Int,iconColorType: statusIconColorType = statusIconColorType.LIGHT){
    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
        this.window.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
            statusBarColor = Color
            decorView.systemUiVisibility = when(iconColorType){
                statusIconColorType.DARK -> View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                statusIconColorType.LIGHT -> 0
            }
        }
    }else{
        this.window.statusBarColor = Color
    }
}
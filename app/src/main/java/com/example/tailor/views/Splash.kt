package com.example.tailor.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import com.example.tailor.R
import com.example.tailor.repositories.ApiRepositoryServices

class Splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val apiService = ApiRepositoryServices.init(this)

        val intent = Intent(this, MainActivity::class.java)
        val timer = object: CountDownTimer(5000, 1000) {
            override fun onTick(millisUntilFinished: Long) {}

            override fun onFinish() {

                startActivity(intent)
                finish()
            }
        }
        timer.start()
    }
}
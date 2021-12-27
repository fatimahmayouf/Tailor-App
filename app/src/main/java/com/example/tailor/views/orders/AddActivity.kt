package com.example.tailor.views.orders

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.tailor.R

class AddActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerViewAdd)
                as NavHostFragment

        navController = navHostFragment.navController


    }
}
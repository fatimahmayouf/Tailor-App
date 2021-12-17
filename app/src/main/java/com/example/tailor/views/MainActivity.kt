package com.example.tailor.views


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.tailor.R
import com.example.tailor.databinding.ActivityMainBinding
import com.example.tailor.views.user.RegisterFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavView.background = null // because I've got something weired in background

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView)
                as NavHostFragment

        navController = navHostFragment.navController
        NavigationUI.setupWithNavController(binding.bottomNavView,navController)
        binding.fab.setOnClickListener {
            // just to test add to firebase
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_layout, AddDesignFragment())
                .commit()
        }
    }
}
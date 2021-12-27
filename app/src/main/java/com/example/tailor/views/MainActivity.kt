package com.example.tailor.views


import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.tailor.R
import com.example.tailor.databinding.ActivityMainBinding
import com.example.tailor.util.getCityName
import com.example.tailor.views.orders.AddDesignFragment
import com.google.android.gms.location.LocationServices
import java.lang.Exception

private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //----------------get user location(latitude and longitude)--------------
        var fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        var getLocation = fusedLocationProviderClient.lastLocation

        checkLocationPermission()
        getLocation.addOnSuccessListener {
            try {
                if (it != null) {
                    var longitude = it.longitude
                    var latitude = it.latitude
                    Log.d(TAG, "Lat:$latitude Long:$longitude")

                    //get city name
                    var cityName = this.getCityName(latitude, longitude)
                    Log.d(TAG, "city name is $cityName")

                    var sharedPreferences = getSharedPreferences("CityName", Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putString("city",cityName)
                    editor.apply()
                }
            } catch (e: Exception) {
                Log.d(TAG, e.message.toString())
            }
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

            //-------- navigation bottom--------------
        binding.bottomNavView.background = null // because I've got something weired in background

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView)
                as NavHostFragment

        navController = navHostFragment.navController
        NavigationUI.setupWithNavController(binding.bottomNavView,navController)

        binding.fab.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_layout, AddDesignFragment())
                .commit()
        }
    }

    fun checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_DENIED
            || ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_DENIED
        ) {

            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                0
            )

        }
    }
    }

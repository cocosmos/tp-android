package com.crea.dev4.mipam.tpandroid

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.*
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.mapSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.core.app.ActivityCompat
import com.crea.dev4.mipam.tpandroid.ui.theme.TPAndroidTheme
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices


class MainActivity : ComponentActivity() {
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
     private var tvLatitude: String by mutableStateOf("")
     private var tvLongitude: String by mutableStateOf("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TPAndroidTheme {
                MyApp()
            }
        }
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        getCurrentLocation()
    }



    /**
     * The App function is the entry point of the app.
     */
    @Composable
    private fun MyApp() {
        var shouldShowBoarding by rememberSaveable { mutableStateOf(true) }
        val shouldGetFormData by rememberSaveable(stateSaver = answerSaver) {
            mutableStateOf(
                Answer(
                    "",
                    "",
                    "",
                    ""
                )
            )
        }

        if (shouldShowBoarding) {
            BoardingScreen(
                onDismiss = { shouldShowBoarding = false }, shouldGetFormData = shouldGetFormData,
            )
        } else {
            FormScreen(
                onDismiss = { shouldShowBoarding = true },
                shouldGetFormData = shouldGetFormData,
                tvLongitude = tvLongitude,
                tvLatitude = tvLatitude,
                getCurrentLocation = { getCurrentLocation() }

            )
        }
    }

    /**
     * This function is used to get the current location of the device.
     */
    @SuppressLint("MissingPermission") // We check for permission before calling the method
     private fun getCurrentLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                fusedLocationProviderClient.lastLocation.addOnCompleteListener(this) { task ->
                    val location: Location? = task.result
                    if (location == null) {
                        requestPermissions()
                        Toast.makeText(this, "No last location", Toast.LENGTH_LONG).show()
                    } else {
                        tvLongitude = location.longitude.toString()
                        tvLatitude = location.latitude.toString()
                    }
                }

            } else {
                Toast.makeText(this, "Please turn on your location...", Toast.LENGTH_LONG)
                    .show()
            }
        } else {
            requestPermissions()
        }
    }

    /**
     * Check if location is enabled
     */

    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }
    /**
     * Check if the app has the necessary permissions to get the user's location
     */
    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }
    /**
     * Request the necessary permissions to get the user's location
     */
    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            100
        )
    }

    /**
     * This function is used to save the state of the form.
     */
    data class Answer(
        var name: String,
        var happy: String,
        var latitude: String,
        var longitude: String
    )
    private val answerSaver = run {
        val nameKey = "Name"
        val happyKey = "Happy"
        val latitudeKey = "Latitude"
        val longitudeKey = "Longitude"
        mapSaver(
            save = { mapOf(nameKey to it.name, happyKey to it.happy, latitudeKey to it.latitude, longitudeKey to it.longitude) },
            restore = { Answer(it[nameKey] as String, it[happyKey] as String, it[latitudeKey] as String, it[longitudeKey] as String ) }
        )
    }

}

package net.thebookofcode.www.amlweather.logic.schedule

import android.Manifest
import android.app.job.JobParameters
import android.app.job.JobService
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.thebookofcode.www.amlweather.logic.repository.MainRepository
import javax.inject.Inject


class WeatherJobService : JobService() {

    @Inject
    lateinit var repository: MainRepository

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate() {
        super.onCreate()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    }

    override fun onStartJob(params: JobParameters?): Boolean {
        // Logic for network call and updating the database
        fetchWeatherAndUpdateDatabase(params)
        return true // true if the job needs to continue running in the background
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        // Logic to stop the job (if needed)
        return true // true to reschedule the job
    }

    private fun fetchWeatherAndUpdateDatabase(params: JobParameters?) {

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    // Use location to make network calls or store it
                    if (location != null) {
                        handleWeatherUpdateWithLocation(location, params)
                    } else {
                        // Handle the case where location is null (e.g., request location updates)
                        requestLocationUpdates(params)
                    }
                }
        } else {
            // Handle the case where permission is not granted
            jobFinished(params, true) // Retry logic
        }
    }

    private fun handleWeatherUpdateWithLocation(location: Location, params: JobParameters?) {
        // Use the location coordinates to fetch weather
        val latitude = location.latitude
        val longitude = location.longitude

        // Call your API using the location to fetch the weather
        // After the network call completes
        CoroutineScope(Dispatchers.IO).launch {
            repository.getLiveWeather(longitude, latitude)
            jobFinished(params, false) // Mark job as finished
        }
    }

    private fun requestLocationUpdates(params: JobParameters?) {
        val locationRequest = LocationRequest.create().apply {
            priority = Priority.PRIORITY_HIGH_ACCURACY
            interval = 10000 // 10 seconds interval
        }

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                object : LocationCallback() {
                    override fun onLocationResult(p0: LocationResult) {
                        p0.lastLocation?.let {
                            handleWeatherUpdateWithLocation(it, params)
                        }
                        fusedLocationClient.removeLocationUpdates(this)
                    }
                },
                null
            )
        }
    }

}
package net.thebookofcode.www.amlweather.ui

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import net.thebookofcode.www.amlweather.R
import net.thebookofcode.www.amlweather.databinding.ActivityMainBinding
import net.thebookofcode.www.amlweather.logic.schedule.WeatherJobService
import net.thebookofcode.www.amlweather.logic.util.ConnectivityObserver

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private lateinit var connectivityObserver: ConnectivityObserver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*connectivityObserver.observer().onEach {
            when (it) {
                ConnectivityObserver.Status.Lost -> {
                    binding.txtStatus.visibility = View.VISIBLE
                    binding.txtStatus.text = "...Network $it"
                }
                ConnectivityObserver.Status.Unavailable -> {
                    binding.txtStatus.visibility = View.VISIBLE
                    binding.txtStatus.text = "...Network $it"
                }
                ConnectivityObserver.Status.Losing -> {
                    binding.txtStatus.visibility = View.GONE
                    binding.txtStatus.text = "...Network $it"
                }
                ConnectivityObserver.Status.Available -> {
                    binding.txtStatus.visibility = View.GONE
                    binding.txtStatus.text = "...Network $it"
                }
            }
        }*/

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.navHostFragment) as NavHostFragment
        val navController: NavController = navHostFragment.navController

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.setupWithNavController(navController)

        val jobScheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler

        val jobInfo = JobInfo.Builder(1, ComponentName(this, WeatherJobService::class.java))
            .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)  // Requires network
            .setRequiresCharging(false)                        // Doesn't need charging
            .setPeriodic(6 * 60 * 60 * 1000)                   // Run every 6 hours
            .build()

        jobScheduler.schedule(jobInfo)


    }


}
package net.thebookofcode.www.amlweather.ui

import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import net.thebookofcode.www.amlweather.R
import net.thebookofcode.www.amlweather.logic.adapter.FutureRecyclerAdapter
import net.thebookofcode.www.amlweather.databinding.FragmentFutureWeatherBinding
import net.thebookofcode.www.amlweather.logic.model.MainViewModel
import net.thebookofcode.www.amlweather.data.local.room.entities.DaysCache
import net.thebookofcode.www.amlweather.logic.util.Resource
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class FutureWeatherFragment : Fragment() {
    private var _binding: FragmentFutureWeatherBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    lateinit var adapter: FutureRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFutureWeatherBinding.inflate(inflater, container, false)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        startShimmer()

        val requestPermissionLauncher =
            registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                if (isGranted) {
                    // Permission is granted. Continue the action or workflow in your
                    // app.
                    startShimmer()
                } else {
                    // Explain to the user that the feature is unavailable because the
                    // features requires a permission that the user has denied. At the
                    // same time, respect the user's decision. Don't link to system
                    // settings in an effort to convince the user to change their
                    // decision.
                    showInContextUI()
                }
            }
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                // You can use the API that requires the permission.
                fusedLocationClient.lastLocation
                    .addOnSuccessListener { location: Location? ->
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            //viewModel.getWeatherByLocation(location.longitude, location.latitude)
                            viewModel.getDays(location.longitude,location.latitude)
                            binding.swipeRefresh.setOnRefreshListener {
                                viewModel.initiate(location.longitude,location.latitude)
                            }

                        } else {
                            checkLocation()
                        }
                    }
            }

            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> {
                // In an educational UI, explain to the user why your app requires this
                // permission for a specific feature to behave as expected. In this UI,
                // include a "cancel" or "no thanks" button that allows the user to
                // continue using your app without granting the permission.
                showInContextUI()
            }
            else -> {
                // You can directly ask for the permission.
                // The registered ActivityResultCallback gets the result of this request.
                requestPermissionLauncher.launch(
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            }
        }

        viewModel.days.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let { result ->
                when (result) {
                    is Resource.Loading -> {
                        startShimmer()
                        shimmerVisible()
                        layoutsGone()
                    }
                    is Resource.Error -> {
                        startShimmer()
                        shimmerVisible()
                        layoutsGone()
                        errorOccurred()
                    }
                    is Resource.Success -> {
                        showDaysInViews(result.data!!)
                        stopShimmer()
                        shimmerGone()
                        layoutsVisible()
                    }
                }
            }
        })

        return binding.root
    }

    private fun showDaysInViews(
        data: List<DaysCache>
    ) {
        binding.txtCondition.text = data[0].icon
        binding.txtTemp.text = farenheitToDegree(data[0].temp)
        binding.txtDate.text = getFormattedDate(data[0].date)
        binding.imgIcon.setImageResource(getIcon(data[0].icon)!!)
        binding.humidity.text = formatPercent(data[0].humidity)
        binding.wind.text = formatKilometers(data[0].windspeed)
        binding.cloudCover.text = formatPercent(data[0].cloudcover)
        adapter = FutureRecyclerAdapter(data)
        binding.recyclerView.adapter = adapter
    }

    private fun startShimmer() {
        binding.shimmerCardLayout.startShimmer()
        binding.shimmerRecyclerLayout.startShimmer()
    }

    private fun layoutsVisible() {
        binding.location.visibility = View.VISIBLE
        binding.cardView.visibility = View.VISIBLE
        binding.recyclerView.visibility = View.VISIBLE
    }

    private fun shimmerGone() {
        binding.shimmerCardLayout.visibility = View.GONE
        binding.shimmerRecyclerLayout.visibility = View.GONE
    }

    private fun layoutsGone() {
        binding.cardView.visibility = View.GONE
        binding.recyclerView.visibility = View.GONE
    }

    private fun shimmerVisible() {
        binding.shimmerCardLayout.visibility = View.VISIBLE
        binding.shimmerRecyclerLayout.visibility = View.VISIBLE
    }

    private fun stopShimmer() {
        binding.shimmerCardLayout.stopShimmer()
        binding.shimmerRecyclerLayout.stopShimmer()
    }

    private fun showInContextUI() {
        AlertDialog.Builder(requireContext()).setTitle("Alert")
            .setMessage("AML Weather requires your location in order to get weather")
            .setCancelable(true)
            .show()
    }

    fun farenheitToDegree(temp: Double): String {
        val doubleTemp = ((temp - 32) * 5) / 9
        return doubleTemp.toInt().toString() + "\u00b0"
    }

    fun getIcon(icon: String): Int? {
        val iconMap = HashMap<String, Int>()
        iconMap["snow"] = R.drawable.snow
        iconMap["rain"] = R.drawable.rain
        //iconMap["fog"] = R.drawable.fog
        iconMap["wind"] = R.drawable.wind
        iconMap["cloudy"] = R.drawable.cloudy
        iconMap["partly-cloudy-day"] = R.drawable.partly_covered_day
        iconMap["partly-cloudy-night"] = R.drawable.partly_covered_night
        iconMap["clear-day"] = R.drawable.clear_day
        iconMap["clear-night"] = R.drawable.clear_night
        iconMap["snow-showers-day"] = R.drawable.snow_showers_day
        iconMap["snow-showers-night"] = R.drawable.snow_showers_night
        iconMap["thunder-rain"] = R.drawable.thunder_shower_day
        iconMap["thunder-showers-day"] = R.drawable.thunder_shower_day
        iconMap["thunder-showers-night"] = R.drawable.thunder_shower_night
        iconMap["showers-day"] = R.drawable.showers_day
        iconMap["showers-night"] = R.drawable.showers_night
        return iconMap[icon]
    }

    fun getFormattedDate(date: String): String {
        var format = SimpleDateFormat("yyyy-MM-dd")
        val newDate: Date = format.parse(date)

        format = SimpleDateFormat("EEEE, dd MMM")
        return format.format(newDate)
    }

    fun formatPercent(item: Double): String {
        return item.toInt().toString() + "%"
    }

    fun formatKilometers(item: Double): String {
        return item.toInt().toString() + "Km/h"
    }

    private fun checkLocation() {
        AlertDialog.Builder(requireContext()).setTitle("Alert")
            .setMessage("AML Weather could not get location, please check Location is turned on and try again")
            .setCancelable(true)
            .show()
    }

    private fun errorOccurred() {
        AlertDialog.Builder(requireContext()).setTitle("Alert")
            .setMessage("Oops, an error occurred")
            .setCancelable(true)
            .show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
package net.thebookofcode.www.amlweather.ui

import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint
import net.thebookofcode.www.amlweather.R
import net.thebookofcode.www.amlweather.adapter.CurrentRecyclerAdapter
import net.thebookofcode.www.amlweather.databinding.FragmentCurrentWeatherBinding
import net.thebookofcode.www.amlweather.entity.Weather
import net.thebookofcode.www.amlweather.model.MainViewModel
import net.thebookofcode.www.amlweather.room.CurrentConditionsCache
import net.thebookofcode.www.amlweather.room.DaysCache
import net.thebookofcode.www.amlweather.room.HoursCache
import net.thebookofcode.www.amlweather.util.DataState
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class CurrentWeatherFragment : Fragment() {
    private var _binding: FragmentCurrentWeatherBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    lateinit var adapter: CurrentRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCurrentWeatherBinding.inflate(inflater, container, false)
        binding.recyclerView.layoutManager = LinearLayoutManager(
            requireActivity(),
            LinearLayoutManager.HORIZONTAL,
            false
        )
        val requestPermissionLauncher =
            registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                if (isGranted) {
                    // Permission is granted. Continue the action or workflow in your
                    // app.
                    //binding.shimmer.startShimmer()
                    startShimmer()
                    //binding.location.visibility = View.VISIBLE
                    binding.noLocation.visibility = View.GONE
                } else {
                    // Explain to the user that the feature is unavailable because the
                    // features requires a permission that the user has denied. At the
                    // same time, respect the user's decision. Don't link to system
                    // settings in an effort to convince the user to change their
                    // decision.
                    showInContextUI()
                    binding.location.visibility = View.GONE
                    binding.noLocation.visibility = View.VISIBLE
                }
            }

            //viewModel.getCurrentConditionsCache()

        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                // You can use the API that requires the permission.
                startShimmer()
                //binding.location.visibility = View.VISIBLE
                binding.noLocation.visibility = View.GONE
                fusedLocationClient.lastLocation
                    .addOnSuccessListener { location: Location? ->
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            viewModel.initiate(location.longitude, location.latitude)
                            viewModel.getCurrentConditions(location.longitude, location.latitude)
                            viewModel.getHours(location.longitude, location.latitude)
                            binding.swipeRefresh.setOnRefreshListener {
                                viewModel.initiate(location.longitude, location.latitude)
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
                binding.location.visibility = View.GONE
                binding.noLocation.visibility = View.VISIBLE
            }
            else -> {
                // You can directly ask for the permission.
                // The registered ActivityResultCallback gets the result of this request.
                requestPermissionLauncher.launch(
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            }
        }
        viewModel.currentCondition.observe(viewLifecycleOwner, Observer {
           when(it){
               is DataState.Loading -> {
                   currentConditionsLoading()
               }
               is DataState.Error -> {
                   currentConditionsLoading()
                   errorOccurred(it.error!!)
               }
               is DataState.Success -> {
                   showCurrentConditionInViews(it.data!!)
                   currentConditionsLoaded()
               }
           }
        })

        viewModel.hours.observe(viewLifecycleOwner, Observer {
            when (it) {
                is DataState.Loading -> {
                    hoursLoading()
                }
                is DataState.Error -> {
                    hoursLoading()
                    errorOccurred(it.error!!)
                }
                is DataState.Success -> {
                    showHoursIViews(it.data!!)
                    hoursLoaded()
                }
            }
        })

        binding.addLocation.setOnClickListener {
            requestPermissionLauncher.launch(
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        }
        binding.getLocation.setOnClickListener {
            requestPermissionLauncher.launch(
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        }

        return binding.root
    }

    private fun hoursLoading() {
        binding.shimmerRecyclerLayout.startShimmer()
        binding.shimmerRecyclerLayout.visibility = View.VISIBLE
        binding.recyclerView.visibility = View.GONE
    }

    private fun hoursLoaded() {
        binding.shimmerRecyclerLayout.stopShimmer()
        binding.shimmerRecyclerLayout.visibility = View.GONE
        binding.recyclerView.visibility = View.VISIBLE
    }

    private fun currentConditionsLoading() = with(binding){
        currentTopShimmer.visibility = View.VISIBLE
        currentTop.visibility = View.GONE
        shimmerLinearLayout.visibility = View.VISIBLE
        linearLayout.visibility = View.GONE
        currentTopShimmer.startShimmer()
        shimmerLinearLayout.startShimmer()
    }

    private fun currentConditionsLoaded() = with(binding){
        currentTopShimmer.visibility = View.GONE
        currentTop.visibility = View.VISIBLE
        shimmerLinearLayout.visibility = View.GONE
        linearLayout.visibility = View.VISIBLE
        currentTopShimmer.stopShimmer()
        shimmerLinearLayout.stopShimmer()
    }

    private fun layoutsVisible() = with(binding) {
        location.visibility = View.VISIBLE
        currentTop.visibility = View.VISIBLE
        linearLayout.visibility = View.VISIBLE
        recyclerView.visibility = View.VISIBLE
    }

    private fun layoutsGone() = with(binding) {
        location.visibility = View.GONE
        currentTop.visibility = View.GONE
        linearLayout.visibility = View.GONE
        recyclerView.visibility = View.GONE
    }

    private fun shimmerGone() = with(binding) {
        currentTopShimmer.visibility = View.GONE
        shimmerRecyclerLayout.visibility = View.GONE
        shimmerLinearLayout.visibility = View.GONE
    }

    private fun shimmerVisible() = with(binding) {
        currentTopShimmer.visibility = View.VISIBLE
        shimmerRecyclerLayout.visibility = View.VISIBLE
        shimmerLinearLayout.visibility = View.VISIBLE
    }

    private fun startShimmer() = with(binding) {
        currentTopShimmer.startShimmer()
        shimmerRecyclerLayout.startShimmer()
        shimmerLinearLayout.startShimmer()
    }

    private fun stopShimmer() = with(binding) {
        shimmerRecyclerLayout.stopShimmer()
        currentTopShimmer.stopShimmer()
        shimmerLinearLayout.stopShimmer()
    }

    private fun showCurrentConditionInViews(
        data: CurrentConditionsCache
    ) = with(binding) {
        humidity.text = formatPercent(data.humidity)
        wind.text = formatKilometers(data.windspeed)
        cloudCover.text = formatPercent(data.cloudcover)
        txtDate.text = getFormattedDate()
        txtTemp.text = farenheitToDegree(data.temp)
        txtAddress.text = data.town
        imgIcon.setImageResource(getIcon(data.icon)!!)
        txtCondition.text = data.condition
    }

    private fun showHoursIViews(data: List<HoursCache>) {
        adapter = CurrentRecyclerAdapter(data)
        binding.recyclerView.adapter = adapter
    }

    private fun showInContextUI() {
        AlertDialog.Builder(requireContext()).setTitle("Alert")
            .setMessage("AML Weather requires your location in order to get weather")
            .setCancelable(true)
            .show()
    }

    private fun checkLocation() {
        AlertDialog.Builder(requireContext()).setTitle("Alert")
            .setMessage("AML Weather could not get location, please check Location is turned on and try again")
            .setCancelable(true)
            .show()
    }

    private fun errorOccurred(e:Throwable) {
        AlertDialog.Builder(requireContext()).setTitle("Alert")
            .setMessage("Oops, an error occurred\n${e.message}")
            .setCancelable(true)
            .show()
    }

    fun farenheitToDegree(temp: Double): String {
        val doubleTemp = ((temp - 32) * 5) / 9
        return doubleTemp.toInt().toString() + "\u00b0"
    }

    fun getFormattedDate(): String {
        val formattedDate = SimpleDateFormat("dd,MMMM", Locale.getDefault()).format(Date())
        return "Today $formattedDate"
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

    fun formatPercent(item: Double): String {
        return item.toInt().toString() + "%"
    }

    fun formatKilometers(item: Double): String {
        return item.toInt().toString() + "Km/h"
    }

    fun betterResolvedAddress(location: Location): String {
        // work on this, Should be able to get it from location parameters tho
        val geocoder = Geocoder(context, Locale.getDefault())
        val addresses: List<Address> =
            geocoder.getFromLocation(location.latitude, location.longitude, 1)
        val cityName: String = addresses[0].getAddressLine(0)
        val parts = cityName.split(",")
        return parts[parts.size - 3] + ", " + parts[parts.size - 1]
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
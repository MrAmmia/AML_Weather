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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import net.thebookofcode.www.amlweather.R
import net.thebookofcode.www.amlweather.adapter.CurrentRecyclerAdapter
import net.thebookofcode.www.amlweather.databinding.FragmentCurrentWeatherBinding
import net.thebookofcode.www.amlweather.model.MainViewModel
import net.thebookofcode.www.amlweather.model.MainViewModelFactory
import net.thebookofcode.www.amlweather.repository.Repository
import java.text.SimpleDateFormat
import java.util.*


class CurrentWeatherFragment : Fragment() {
    private var _binding: FragmentCurrentWeatherBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels {
        MainViewModelFactory(Repository())
    }
    private lateinit var fusedLocationClient: FusedLocationProviderClient

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
        val requestPermissionLauncher =
            registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                if (isGranted) {
                    // Permission is granted. Continue the action or workflow in your
                    // app.
                    //binding.shimmer.startShimmer()
                    binding.currentTopShimmer.startShimmer()
                    binding.shimmerRecyclerLayout.startShimmer()
                    binding.shimmerLinearLayout.startShimmer()
                    //binding.location.visibility = View.VISIBLE
                    binding.noLocation.visibility = View.GONE
                } else {
                    // Explain to the user that the feature is unavailable because the
                    // features requires a permission that the user has denied. At the
                    // same time, respect the user's decision. Don't link to system
                    // settings in an effort to convince the user to change their
                    // decision.
                }
            }
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                // You can use the API that requires the permission.
                //binding.shimmer.startShimmer()
                binding.currentTopShimmer.startShimmer()
                binding.shimmerRecyclerLayout.startShimmer()
                binding.shimmerLinearLayout.startShimmer()
                //binding.location.visibility = View.VISIBLE
                binding.noLocation.visibility = View.GONE
                fusedLocationClient.lastLocation
                    .addOnSuccessListener { location: Location? ->
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            viewModel.getWeatherByLocation(location.longitude, location.latitude)
                            viewModel.myResponseByLocation.observe(viewLifecycleOwner, Observer {
                                Log.i("Response", it.body()!!.currentConditions.toString())

                                binding.currentTopShimmer.stopShimmer()
                                binding.shimmerRecyclerLayout.stopShimmer()
                                binding.shimmerLinearLayout.stopShimmer()
                                binding.currentTopShimmer.visibility = View.GONE
                                binding.shimmerRecyclerLayout.visibility = View.GONE
                                binding.shimmerLinearLayout.visibility = View.GONE
                                binding.location.visibility = View.VISIBLE
                                binding.currentTop.visibility = View.VISIBLE
                                binding.linearLayout.visibility = View.VISIBLE
                                binding.recyclerView.visibility = View.VISIBLE

                                binding.humidity.text = formatPercent(it.body()!!.currentConditions.humidity)
                                binding.wind.text = formatKilometers(it.body()!!.currentConditions.windspeed)
                                binding.cloudCover.text = formatPercent(it.body()!!.currentConditions.cloudcover)

                                binding.txtAddress.text = betterResolvedAddress(location)
                                binding.txtDate.text = getFormattedDate()
                                binding.txtTemp.text = farenheitToDegree(it.body()!!.currentConditions.temp)
                                binding.imgIcon.setImageResource(getIcon(it.body()!!.currentConditions.icon)!!)
                                binding.txtCondition.text = it.body()!!.currentConditions.condition


                                val adapter = CurrentRecyclerAdapter(it.body()!!.days[0].hours!!)
                                binding.recyclerView.layoutManager = LinearLayoutManager(
                                    requireActivity(),
                                    LinearLayoutManager.HORIZONTAL,
                                    false
                                )
                                binding.recyclerView.adapter = adapter
                            })
                        }else{}
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

    fun getFormattedDate():String{
        val formattedDate = SimpleDateFormat("dd,MMMM",Locale.getDefault()).format(Date())
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

    fun formatPercent(item:Double):String{
        return item.toInt().toString() + "%"
    }

    fun formatKilometers(item:Double):String{
        return item.toInt().toString() + "Km/h"
    }

    fun betterResolvedAddress(location: Location): String {
        // work on this, Should be able to get it from location parameters tho
        val geocoder = Geocoder(context, Locale.getDefault())
        val addresses: List<Address> =
            geocoder.getFromLocation(location.latitude, location.longitude, 1)
        val cityName: String = addresses[0].getAddressLine(0)
        val parts = cityName.split(",")
        val resolvedAddress = parts[parts.size-3] +", "+ parts[parts.size-1]
        return resolvedAddress
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
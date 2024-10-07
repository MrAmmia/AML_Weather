package net.thebookofcode.www.amlweather.ui

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint
import net.thebookofcode.www.amlweather.data.local.room.entities.CurrentConditionCache
import net.thebookofcode.www.amlweather.data.local.room.entities.HourCache
import net.thebookofcode.www.amlweather.databinding.FragmentCurrentWeatherBinding
import net.thebookofcode.www.amlweather.logic.adapter.CurrentRecyclerAdapter
import net.thebookofcode.www.amlweather.logic.model.MainViewModel
import net.thebookofcode.www.amlweather.logic.util.Resource
import net.thebookofcode.www.amlweather.ui.util.Utilities.Companion.checkLocation
import net.thebookofcode.www.amlweather.ui.util.Utilities.Companion.errorOccurred
import net.thebookofcode.www.amlweather.ui.util.Utilities.Companion.farenheitToDegree
import net.thebookofcode.www.amlweather.ui.util.Utilities.Companion.formatKilometers
import net.thebookofcode.www.amlweather.ui.util.Utilities.Companion.formatPercent
import net.thebookofcode.www.amlweather.ui.util.Utilities.Companion.getFormattedDate
import net.thebookofcode.www.amlweather.ui.util.Utilities.Companion.getIcon
import net.thebookofcode.www.amlweather.ui.util.Utilities.Companion.showInContextUI

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
                    showInContextUI(requireContext())
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
                binding.noLocation.visibility = View.GONE
                fusedLocationClient.lastLocation
                    .addOnSuccessListener { location: Location? ->
                        // Got last known location. In some rare situations this can be null.
                        viewModel.isWeatherCacheAvailableAndFresh()
                            .observe(viewLifecycleOwner, Observer { cacheIsAvailableAndFresh ->
                                if (cacheIsAvailableAndFresh) {
                                    viewModel.getCachedWeather()
                                    binding.swipeRefresh.setOnRefreshListener {
                                        viewModel.getCachedWeather()
                                    }
                                } else {
                                    if (location != null) {
                                        viewModel.getLiveWeather(location.longitude, location.latitude)
                                        binding.swipeRefresh.setOnRefreshListener {
                                            viewModel.getLiveWeather(location.longitude, location.latitude)
                                        }
                                    } else {
                                        // location is null, so check if cache is available
                                        checkLocation(requireContext())
                                    }
                                }
                            })

                    }
            }

            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> {
                // In an educational UI, explain to the user why your app requires this
                // permission for a specific feature to behave as expected. In this UI,
                // include a "cancel" or "no thanks" button that allows the user to
                // continue using your app without granting the permission.
                showInContextUI(requireContext())
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

        viewModel.weather.observe(viewLifecycleOwner, Observer{
            it.getContentIfNotHandled()?.let { result ->
                when(result){
                    is Resource.Loading -> {
                        currentConditionsLoading()
                        hoursLoading()
                    }
                    
                    is Resource.Error -> {
                        currentConditionsLoading()
                        hoursLoading()
                        errorOccurred(requireContext(), result.error!!)
                    }

                    is Resource.Success -> {
                        showCurrentConditionInViews(result.data!!.currentConditionCache)
                        showHoursIViews(result.data.hourCache)
                        currentConditionsLoaded()
                        hoursLoaded()
                    }
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

        binding.txtNext.setOnClickListener{
            findNavController().navigate(
                CurrentWeatherFragmentDirections.actionCurrentWeatherFragmentToFutureWeatherFragment()
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

    private fun currentConditionsLoading() = with(binding) {
        currentTopShimmer.visibility = View.VISIBLE
        currentTop.visibility = View.GONE
        shimmerLinearLayout.visibility = View.VISIBLE
        linearLayout.visibility = View.GONE
        currentTopShimmer.startShimmer()
        shimmerLinearLayout.startShimmer()
    }

    private fun currentConditionsLoaded() = with(binding) {
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
        data: CurrentConditionCache
    ) = with(binding) {
        humidity.text = formatPercent(data.humidity)
        wind.text = formatKilometers(data.windSpeed)
        cloudCover.text = formatPercent(data.cloudCover)
        txtDate.text = getFormattedDate()
        txtTemp.text = farenheitToDegree(data.temp)
        txtAddress.text = data.town
        imgIcon.setImageResource(getIcon(data.icon))
        txtCondition.text = data.condition
    }

    private fun showHoursIViews(data: List<HourCache>) {
        adapter = CurrentRecyclerAdapter(data)
        binding.recyclerView.adapter = adapter
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
package net.thebookofcode.www.amlweather

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach
import net.thebookofcode.www.amlweather.adapter.ViewPagerAdapter
import net.thebookofcode.www.amlweather.databinding.ActivityMainBinding
import net.thebookofcode.www.amlweather.model.MainViewModel
import net.thebookofcode.www.amlweather.ui.CurrentWeatherFragment
import net.thebookofcode.www.amlweather.ui.FutureWeatherFragment
import net.thebookofcode.www.amlweather.ui.OtherCitiesFragment
import net.thebookofcode.www.amlweather.util.ConnectivityObserver

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private lateinit var connectivityObserver: ConnectivityObserver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        connectivityObserver.observer().onEach {
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
        }

        val fragmentList = arrayListOf<Fragment>(
            CurrentWeatherFragment(),
            FutureWeatherFragment(),
            OtherCitiesFragment()
        )
        val adapter = ViewPagerAdapter(
            fragmentList,
            this.supportFragmentManager,
            lifecycle
        )
        binding.viewPager2.adapter = adapter
    }


}
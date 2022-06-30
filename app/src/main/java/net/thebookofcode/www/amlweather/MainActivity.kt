package net.thebookofcode.www.amlweather

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import net.thebookofcode.www.amlweather.adapter.ViewPagerAdapter
import net.thebookofcode.www.amlweather.databinding.ActivityMainBinding
import net.thebookofcode.www.amlweather.model.MainViewModel
import net.thebookofcode.www.amlweather.ui.CurrentWeatherFragment
import net.thebookofcode.www.amlweather.ui.FutureWeatherFragment
import net.thebookofcode.www.amlweather.ui.OtherCitiesFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
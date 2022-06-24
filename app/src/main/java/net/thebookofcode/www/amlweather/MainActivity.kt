package net.thebookofcode.www.amlweather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import net.thebookofcode.www.amlweather.adapter.ViewPagerAdapter
import net.thebookofcode.www.amlweather.databinding.ActivityMainBinding
import net.thebookofcode.www.amlweather.model.MainViewModel
import net.thebookofcode.www.amlweather.model.MainViewModelFactory
import net.thebookofcode.www.amlweather.repository.Repository
import net.thebookofcode.www.amlweather.ui.CurrentWeather
import net.thebookofcode.www.amlweather.ui.FutureWeatherFragment
import net.thebookofcode.www.amlweather.ui.OtherCitiesFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val repository = Repository()
        val mainViewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this,mainViewModelFactory)[MainViewModel::class.java]
        viewModel.getWeatherByLocation(6.543941,3.3680577)
        viewModel.myResponseByLocation.observe(this, Observer {
            Log.i("Response",it.body().toString())
            it.body().
        })

        val fragmentList = arrayListOf<Fragment>(
            CurrentWeather(),
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
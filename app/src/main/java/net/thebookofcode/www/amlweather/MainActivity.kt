package net.thebookofcode.www.amlweather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import net.thebookofcode.www.amlweather.model.MainViewModel
import net.thebookofcode.www.amlweather.model.MainViewModelFactory
import net.thebookofcode.www.amlweather.repository.Repository

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val repository = Repository()
        val mainViewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this,mainViewModelFactory)[MainViewModel::class.java]
        viewModel.getWeather()
        viewModel.myResponse.observe(this, Observer {
            Log.d("Response",it.toString())
        })
    }
}
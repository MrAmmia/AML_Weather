package net.thebookofcode.www.amlweather.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import net.thebookofcode.www.amlweather.entity.Weather
import net.thebookofcode.www.amlweather.repository.Repository
import retrofit2.Response

class MainViewModel(private val repository: Repository):ViewModel() {
    val myResponse:MutableLiveData<Response<Weather>> = MutableLiveData()
    val myResponseByLocation:MutableLiveData<Response<Weather>> = MutableLiveData()

    fun getWeather() {
        viewModelScope.launch {
            val response = repository.getWeather()
            myResponse.value = response
        }
    }

    fun getWeatherByLocation(longNum:Double,latNum:Double){
        viewModelScope.launch {
            val response = repository.getWeatherByLocation(longNum, latNum)
            myResponseByLocation.value = response
        }
    }
}
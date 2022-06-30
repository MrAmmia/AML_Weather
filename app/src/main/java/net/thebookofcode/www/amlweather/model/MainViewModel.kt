package net.thebookofcode.www.amlweather.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import net.thebookofcode.www.amlweather.entity.Weather
import net.thebookofcode.www.amlweather.repository.Repository
import retrofit2.Response

class MainViewModel(private val repository: Repository) : ViewModel() {
    val towns = arrayListOf<String>()
    val myResponse: MutableLiveData<Response<Weather>> = MutableLiveData()
    val myResponseOtherCitiesWeather: MutableLiveData<List<Response<Weather>>> = MutableLiveData()
    val otherCitiesList: MutableList<Response<Weather>> = mutableListOf()
    val myResponseByLocation: MutableLiveData<Response<Weather>> = MutableLiveData()
    val myResponseByTown: MutableLiveData<Response<Weather>> = MutableLiveData()

    init {
        getOtherCitiesWeather()
    }

    fun getWeatherByTown(town: String) {
        viewModelScope.launch {
            val response = repository.getWeatherByTown(town)
            myResponseByTown.value = response
        }
    }

    fun getWeather() {
        viewModelScope.launch {
            val response = repository.getWeather()
            myResponse.value = response
        }
    }

    fun getWeatherByLocation(longNum: Double, latNum: Double) {
        viewModelScope.launch {
            val response = repository.getWeatherByLocation(longNum, latNum)
            myResponseByLocation.value = response
        }
    }

    fun getOtherCitiesWeather() {
        viewModelScope.launch {
            towns.add("London,Uk")
            towns.add("Berlin,Germany")
            towns.add("Madrid,Spain")
            towns.add("Cairo,Egypt")
            towns.add("Dhaka,Bangladesh")
            towns.add("Bogotta,Colombia")
            towns.add("Paris,France")
            towns.add("Johannesburg,South Africa")
            towns.add("Lagos,Nigeria")
            towns.add("Lisbon,Portugal")
            for (town: String in towns) {
                otherCitiesList.add(repository.getWeatherByTown(town))
            }
            myResponseOtherCitiesWeather.postValue(otherCitiesList)
        }

    }
}
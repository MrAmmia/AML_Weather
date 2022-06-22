package net.thebookofcode.www.amlweather.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import net.thebookofcode.www.amlweather.entity.Weather
import net.thebookofcode.www.amlweather.repository.Repository

class MainViewModel(private val repository: Repository):ViewModel() {
    val myResponse:MutableLiveData<Weather> = MutableLiveData()

    fun getWeather() {
        viewModelScope.launch {
            val response = repository.getWeather()
            myResponse.value = response
        }
    }
}
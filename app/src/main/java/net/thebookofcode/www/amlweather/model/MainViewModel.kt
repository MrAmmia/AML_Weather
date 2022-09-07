package net.thebookofcode.www.amlweather.model

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import net.thebookofcode.www.amlweather.entity.*
import net.thebookofcode.www.amlweather.repository.MainRepository
import net.thebookofcode.www.amlweather.room.CurrentConditionsCache
import net.thebookofcode.www.amlweather.room.DaysCache
import net.thebookofcode.www.amlweather.room.HoursCache
import net.thebookofcode.www.amlweather.util.DataState
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject constructor(
    private val repository: MainRepository
) : ViewModel() {
    private val _days = MutableLiveData<DataState<List<DaysCache>>>()
    val days:LiveData<DataState<List<DaysCache>>> = _days

    private val _hours = MutableLiveData<DataState<List<HoursCache>>>()
    val hours:LiveData<DataState<List<HoursCache>>> = _hours

    private val _currentCondition = MutableLiveData<DataState<CurrentConditionsCache>>()
    val currentCondition:LiveData<DataState<CurrentConditionsCache>> = _currentCondition

    lateinit var myResponseByLocation: LiveData<DataState<Weather>>
    lateinit var myResponseByTown: LiveData<DataState<OtherWeather>>
    val towns = arrayListOf<String>()
    //val otherCitiesList : List<>
    //val myResponseByTown: MutableLiveData<Response<OtherWeather>> = MutableLiveData()

    //val myResponseByLocation: MutableLiveData<Resource<Weather>> = MutableLiveData()
    /*val towns = arrayListOf<String>()
    val myResponse: MutableLiveData<Response<Weather>> = MutableLiveData()
    val myResponseOtherCitiesWeather: MutableLiveData<List<Response<Weather>>> = MutableLiveData()
    val otherCitiesList: MutableList<Response<Weather>> = mutableListOf()
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

    }*/

    /*init {
        getOtherCitiesWeather()
    }

    fun getWeatherByLocation(longNum: Double, latNum: Double) {
        viewModelScope.launch {
            val response = repository.getWeatherByLocation(longNum, latNum).asLiveData()
            myResponseByLocation = response
            //myResponseByLocation.value = response
        }
    }

    fun getWeatherByTown(town: String) {
        viewModelScope.launch {
            val response = repository.getWeatherByTown(town).asLiveData()
            myResponseByTown = response
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
                val response = repository.getWeatherByTown(town).asLiveData()
                //otherCitiesList.add(response)
            }

            //myResponseOtherCitiesWeather.postValue(otherCitiesList)
        }

    }*/

    fun getCurrentConditions(longNum: Double, latNum: Double){
        val cachedCurrentConditions = repository.getCachedCurrentConditions()
        if(cachedCurrentConditions != null){
            //_currentCondition.postValue(cachedCurrentConditions)
            return
        }
        viewModelScope.launch {
            repository.getLiveCurrentConditions(longNum, latNum).onEach {
                _currentCondition.value =it
            }.launchIn(viewModelScope)
        }
    }

    fun getDays(longNum: Double, latNum: Double){
        val cachedDays = repository.getCachedDays()
        if (cachedDays != null){
            //_days.postValue(cachedDays)
            return
        }
        viewModelScope.launch {
            repository.getLiveDays(longNum, latNum).onEach {
                _days.value = it
            }.launchIn(viewModelScope)
        }
    }

    fun getHours(longNum: Double,latNum: Double){
        val cachedHours = repository.getCachedHours()
        if(cachedHours != null){
            //_hours.postValue(cachedHours)
            return
        }
        viewModelScope.launch {
            repository.getLiveHours(longNum, latNum).onEach {
                _hours.value = it
            }.launchIn(viewModelScope)
        }
    }
}
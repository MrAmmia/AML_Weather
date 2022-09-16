package net.thebookofcode.www.amlweather.model

import android.util.Log
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import net.thebookofcode.www.amlweather.entity.*
import net.thebookofcode.www.amlweather.repository.MainRepository
import net.thebookofcode.www.amlweather.room.CurrentConditionsCache
import net.thebookofcode.www.amlweather.room.DaysCache
import net.thebookofcode.www.amlweather.room.HoursCache
import net.thebookofcode.www.amlweather.room.OtherWeatherCache
import net.thebookofcode.www.amlweather.util.DataState
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject constructor(
    private val repository: MainRepository
) : ViewModel() {
    private val _days = MutableLiveData<DataState<List<DaysCache>>>()
    var days: LiveData<DataState<List<DaysCache>>> = _days

    private val _hours = MutableLiveData<DataState<List<HoursCache>>>()
    var hours: LiveData<DataState<List<HoursCache>>> = _hours

    private val _currentCondition = MutableLiveData<DataState<CurrentConditionsCache>>()
    var currentCondition: LiveData<DataState<CurrentConditionsCache>> = _currentCondition

    private val _others = MutableLiveData<DataState<List<OtherWeatherCache>>>()
    val otherWeather = arrayListOf<DataState<OtherWeatherCache>>()
    var other: LiveData<DataState<List<OtherWeatherCache>>> = _others

    lateinit var myResponseByLocation: LiveData<DataState<Weather>>
    lateinit var myResponseByTown: LiveData<DataState<OtherWeather>>
    val towns = arrayListOf<String>()


    //Use this method to refresh
    fun initiate(longNum: Double, latNum: Double) {
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
        viewModelScope.launch {
            repository.initiate(longNum, latNum)
            repository.getLiveOtherWeather(towns).onEach {
                _others.value = it
            }.launchIn(viewModelScope)
        }
    }

    fun getCurrentConditions(longNum: Double, latNum: Double) {

        viewModelScope.launch {
            if (repository.getCachedCurrentConditionCount() != 0) {
                repository.getCachedCurrentConditions().onEach {
                    _currentCondition.value = it
                }.launchIn(viewModelScope)
            } else {
                repository.getLiveCurrentConditions(longNum, latNum).onEach {
                    _currentCondition.value = it
                    Log.i("Live Current Conditions", it.data.toString())
                }.launchIn(viewModelScope)
            }
        }
    }

    fun getDays(longNum: Double, latNum: Double) {
        viewModelScope.launch {
            if (repository.getCachedDaysCount() != 0) {
                repository.getCachedDays().onEach {
                    _days.value = it
                }.launchIn(viewModelScope)
            } else {
                repository.getLiveDays(longNum, latNum).onEach {
                    _days.value = it
                }.launchIn(viewModelScope)
            }
        }
    }

    fun getHours(longNum: Double, latNum: Double) {

        viewModelScope.launch {
            if (repository.getCachedHoursCount() != 0) {
                repository.getCachedHours().onEach {
                    _hours.value = it
                }.launchIn(viewModelScope)
            } else {
                repository.getLiveHours(longNum, latNum).onEach {
                    _hours.value = it
                    Log.i("Live Hours", it.data.toString())
                }.launchIn(viewModelScope)
            }
        }
    }

    fun getOthers(towns: ArrayList<String>) {
        viewModelScope.launch {
            try {
                repository.getCachedOtherWeather().onEach {
                    _others.value = it
                }.launchIn(viewModelScope)
            }catch (e:NullPointerException){
                repository.getLiveOtherWeather(towns).onEach {
                    _others.value = it
                }.launchIn(viewModelScope)
            }
            /*if (repository.getCachedOthersCount() != 0) {

            } else {

            }*/
        }
    }
}
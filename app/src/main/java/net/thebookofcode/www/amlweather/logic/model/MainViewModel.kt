package net.thebookofcode.www.amlweather.logic.model

import android.util.Log
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import net.thebookofcode.www.amlweather.data.local.room.entities.*
import net.thebookofcode.www.amlweather.logic.repository.MainRepository
import net.thebookofcode.www.amlweather.data.remote.responses.entities.OtherWeather
import net.thebookofcode.www.amlweather.data.remote.responses.entities.Weather
import net.thebookofcode.www.amlweather.logic.util.Event
import net.thebookofcode.www.amlweather.logic.util.Resource
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    private val _days = MutableLiveData<Event<Resource<List<DaysCache>>>>()
    var days: LiveData<Event<Resource<List<DaysCache>>>> = _days

    private val _hours = MutableLiveData<Event<Resource<List<HoursCache>>>>()
    var hours: LiveData<Event<Resource<List<HoursCache>>>> = _hours

    private val _currentCondition = MutableLiveData<Event<Resource<CurrentConditionsCache>>>()
    var currentCondition: LiveData<Event<Resource<CurrentConditionsCache>>> = _currentCondition

    private val _others = MutableLiveData<Event<Resource<List<OtherWeatherCache>>>>()
    //val otherWeather = arrayListOf<Event<Resource<OtherWeatherCache>>>()
    var other: LiveData<Event<Resource<List<OtherWeatherCache>>>> = _others

    lateinit var myResponseByLocation: LiveData<Event<Resource<Weather>>>
    lateinit var myResponseByTown: LiveData<Event<Resource<OtherWeather>>>
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
            try {
                repository.getLiveOtherWeather(towns).onEach {
                    _others.value = Event(it)
                }.launchIn(viewModelScope)
            } catch (e: Exception) {
                _others.value= Event(Resource.Error(e.message!!))
            }
        }
    }

    fun getCurrentConditions(longNum: Double, latNum: Double) {

        viewModelScope.launch {
            try {
                if (repository.getCachedCurrentConditionCount() != 0) {
                    repository.getCachedCurrentConditions().onEach {
                        _currentCondition.value = Event(it)
                    }.launchIn(viewModelScope)
                } else {
                    repository.getLiveCurrentConditions(longNum, latNum).onEach {
                        _currentCondition.value = Event(it)
                        Log.i("Live Current Conditions", it.data.toString())
                    }.launchIn(viewModelScope)
                }
            } catch (e: Exception) {
                _currentCondition.value= Event(Resource.Error(e.message!!))
            }
        }
    }

    fun getDays(longNum: Double, latNum: Double) {
        viewModelScope.launch {
            try {
                if (repository.getCachedDaysCount() != 0) {
                    repository.getCachedDays().onEach {
                        _days.value = Event(it)
                    }.launchIn(viewModelScope)
                } else {
                    repository.getLiveDays(longNum, latNum).onEach {
                        _days.value = Event(it)
                    }.launchIn(viewModelScope)
                }
            } catch (e: Exception) {
                _days.value = Event(Resource.Error(e.message!!))
            }
        }
    }

    fun getHours(longNum: Double, latNum: Double) {

        viewModelScope.launch {
            try {
                if (repository.getCachedHoursCount() != 0) {
                    repository.getCachedHours().onEach {
                        _hours.value = Event(it)
                    }.launchIn(viewModelScope)
                } else {
                    repository.getLiveHours(longNum, latNum).onEach {
                        _hours.value = Event(it)
                        Log.i("Live Hours", it.data.toString())
                    }.launchIn(viewModelScope)
                }
            } catch (e: Exception) {
                _hours.postValue(Event(Resource.Error(e.message!!)))
            }
        }
    }

    fun getOthers(towns: ArrayList<String>) {
        viewModelScope.launch {
            try {
                repository.getCachedOtherWeather().onEach {
                    _others.value = Event(it)
                }.launchIn(viewModelScope)
            } catch (e: Exception) {
                repository.getLiveOtherWeather(towns).onEach {
                    _others.value = Event(Resource.Error(e.message!!))
                }.launchIn(viewModelScope)
            }
            /*if (repository.getCachedOthersCount() != 0) {

            } else {

            }*/
        }
    }
}
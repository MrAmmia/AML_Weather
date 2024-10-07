package net.thebookofcode.www.amlweather.logic.model

import android.util.Log
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import net.thebookofcode.www.amlweather.data.local.room.entities.*
import net.thebookofcode.www.amlweather.logic.repository.MainRepository
import net.thebookofcode.www.amlweather.data.ui.CurrentWeatherFragmentData
import net.thebookofcode.www.amlweather.data.ui.FutureWeatherFragmentData
import net.thebookofcode.www.amlweather.logic.util.Event
import net.thebookofcode.www.amlweather.logic.util.Resource
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    private val _days = MutableLiveData<Event<Resource<FutureWeatherFragmentData>>>()
    var days: LiveData<Event<Resource<FutureWeatherFragmentData>>> = _days

    private val _hours = MutableLiveData<Event<Resource<List<HourCache>>>>()
    var hours: LiveData<Event<Resource<List<HourCache>>>> = _hours

    private val _weather = MutableLiveData<Event<Resource<CurrentWeatherFragmentData>>>()
    var weather: LiveData<Event<Resource<CurrentWeatherFragmentData>>> = _weather

    private val _others = MutableLiveData<Event<Resource<List<OtherWeatherCache>>>>()

    //val otherWeather = arrayListOf<Event<Resource<OtherWeatherCache>>>()
    var other: LiveData<Event<Resource<List<OtherWeatherCache>>>> = _others

    val towns = arrayListOf<String>()


    //Use this method to refresh
//    fun initiate(longNum: Double, latNum: Double) {
//        towns.add("London,Uk")
//        towns.add("Berlin,Germany")
//        towns.add("Madrid,Spain")
//        towns.add("Cairo,Egypt")
//        towns.add("Dhaka,Bangladesh")
//        towns.add("Bogotta,Colombia")
//        towns.add("Paris,France")
//        towns.add("Johannesburg,South Africa")
//        towns.add("Lagos,Nigeria")
//        towns.add("Lisbon,Portugal")
//        viewModelScope.launch {
//            //repository.initiate(longNum, latNum)
//            try {
//                repository.getLiveOtherWeather(towns).onEach {
//                    _others.value = Event(it)
//                }.launchIn(viewModelScope)
//            } catch (e: Exception) {
//                _others.value = Event(Resource.Error(e.message!!))
//            }
//        }
//    }

    fun getLiveWeather(longNum: Double, latNum: Double) {
        viewModelScope.launch {
            try {
                repository.getLiveWeather(longNum, latNum).onEach {
                    _weather.value = Event((it))
                }.launchIn(viewModelScope)

            } catch (e: Exception) {
                _weather.value = Event(Resource.Error(e.message!!))
            }
        }
    }

    fun getCachedWeather() {
        viewModelScope.launch {
            try {
                repository.getCachedLiveWeather().onEach {
                    _weather.value = Event(it)
                }.launchIn(viewModelScope)
            } catch (e: Exception) {
                _weather.value = Event(Resource.Error(e.message!!))
            }
        }
    }

    fun isWeatherCacheAvailableAndFresh(): LiveData<Boolean> {
        val result = MutableLiveData<Boolean>()
        viewModelScope.launch {
            result.value = repository.getCachedWeatherCount() > 0 && repository.isWeatherCacheFresh()
        }
        return result
    }

    fun isFutureWeatherCacheAvailableAndFresh(): LiveData<Boolean> {
        val result = MutableLiveData<Boolean>()
        viewModelScope.launch {
            result.value = repository.isDayCacheFreshAndAvailable()
        }
        return result
    }

    fun updateCurrentConditionCache(address: String) {
        viewModelScope.launch {
            try {
                repository.updateCachedCurrentConditions(address)
            } catch (e: Exception) {
                Log.e("UPDATE CURRENT CONDITION CACHE", "FAILED")
            }
        }
    }

    fun getLiveDays(longNum: Double, latNum: Double) {
        viewModelScope.launch {
            try {
                repository.getLiveDays(longNum, latNum).onEach {
                        _days.value = Event(it)
                    }.launchIn(viewModelScope)

            } catch (e: Exception) {
                _days.value = Event(Resource.Error(e.message!!))
            }
        }
    }

    fun getCachedDays() {
        viewModelScope.launch {
            try {
                repository.getCachedDays().onEach {
                    _days.value = Event(it)
                }.launchIn(viewModelScope)
            } catch (e: Exception) {
                _days.value = Event(Resource.Error(e.message!!))
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

    fun getOtherWeatherCache() {
        viewModelScope.launch {
            try {
                repository.getCachedOtherWeather().onEach {
                    _others.value = Event(it)
                }.launchIn(viewModelScope)
            } catch (e: Exception) {
                _others.value = Event(Resource.Error(e.message!!))
            }
        }
    }

    fun isOtherWeatherCacheAvailable(): LiveData<Boolean> {
        val result = MutableLiveData<Boolean>()
        viewModelScope.launch {
            result.value = repository.getCachedOthersCount() > 0
        }
        return result
    }
}
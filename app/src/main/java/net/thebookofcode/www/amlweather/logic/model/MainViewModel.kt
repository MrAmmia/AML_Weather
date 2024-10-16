package net.thebookofcode.www.amlweather.logic.model

import android.util.Log
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import net.thebookofcode.www.amlweather.R
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

//    private val _hours = MutableLiveData<Event<Resource<List<HourCache>>>>()
//    var hours: LiveData<Event<Resource<List<HourCache>>>> = _hours

    private val _weather = MutableLiveData<Event<Resource<CurrentWeatherFragmentData>>>()
    var weather: LiveData<Event<Resource<CurrentWeatherFragmentData>>> = _weather

    private val _others = MutableLiveData<Event<Resource<List<OtherWeatherCache>>>>()

    //val otherWeather = arrayListOf<Event<Resource<OtherWeatherCache>>>()
    var other: LiveData<Event<Resource<List<OtherWeatherCache>>>> = _others

    val hours: Flow<PagingData<HourCache>> = repository.getHours().cachedIn(viewModelScope)



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
            result.value =
                repository.getCachedWeatherCount() > 0 && repository.isWeatherCacheFresh()
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

//    fun getHours() {
//        hoursState.update { it.copy(isLoading = true) }
//
//        viewModelScope.launch {
//            val result = repository.getHours()
//            hoursState.update {
//                when (result) {
//                    is Result.Success -> it.copy(hours = result.data, isLoading = false)
//                    is Result.Error -> {
//                        val errorMessages = it.errorMessages + ErrorMessage(
//                            id = UUID.randomUUID().mostSignificantBits,
//                            messageId = R.string.load_error
//                        )
//                        it.copy(errorMessages = errorMessages, isLoading = false)
//                    }
//                }
//            }
//        }
//    }
}
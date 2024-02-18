package cn.xyh.weatherapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cn.xyh.weatherapp.data.mappers.toWeatherInfo
import cn.xyh.weatherapp.data.remote.WeatherApi
import kotlinx.coroutines.launch

class WeatherViewModel: ViewModel() {

    val weatherApi = WeatherApi.instance()

    var latitude by mutableStateOf(0.0)
    var longitude by mutableStateOf(0.0)

    var state by mutableStateOf(WeatherState())
        private set
    fun loadWeatherInfo() {
        viewModelScope.launch {
            val result = weatherApi.getWeatherData(latitude, longitude).toWeatherInfo()
            state = state.copy(
                weatherInfo = result,
                isLoading = false,
                error = null
            )
        }
    }
}
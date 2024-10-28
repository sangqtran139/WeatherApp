package com.sangtq.weatherdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sangtq.domain.ForecastWeatherUseCase
import com.sangtq.model.basenetwork.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherDetailViewModel @Inject constructor(
    private val forecastWeatherUseCase: ForecastWeatherUseCase
) : ViewModel() {
    private val _weatherDetailUiState = MutableStateFlow<Resource<Any>?>(null)
    val weatherDetailUiState: StateFlow<Resource<Any>?> = _weatherDetailUiState

    init {
        getWeatherDetail()
    }

    private fun getWeatherDetail() = viewModelScope.launch {
        _weatherDetailUiState.value = Resource.Loading()

        val result = forecastWeatherUseCase.getForecastWeather("VietNam", 3, "", "")

        result.fold(
            onSuccess = {
                _weatherDetailUiState.value = Resource.Success(it)
            },
            onFailure = {
                _weatherDetailUiState.value = Resource.Error(it.message ?: "Unknown Error", it)
            }
        )
    }
}

enum class NoteDetailWeather {
    PRECIPITATION,
    WINDY,
    HUMIDITY,
    INDEX_UV
}
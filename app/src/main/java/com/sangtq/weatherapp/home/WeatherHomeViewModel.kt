package com.sangtq.weatherapp.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sangtq.domain.CurrentWeatherUseCase
import com.sangtq.model.basenetwork.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherHomeViewModel @Inject constructor(private val currentWeatherUseCase: CurrentWeatherUseCase): ViewModel() {
    private val _weatherHomeUiState = MutableStateFlow<Resource<Any>?>(null)
    val currentWeatherUiState: StateFlow<Resource<Any>?>
        get() = _weatherHomeUiState

    fun getCurrentWeather() = viewModelScope.launch {
        _weatherHomeUiState.value = Resource.Loading()

        val result = currentWeatherUseCase.getCurrentWeather("London")

        result.fold(
            onSuccess = {
                _weatherHomeUiState.value = Resource.Success(it)
            },
            onFailure = {
                _weatherHomeUiState.value = Resource.Error("Unknown Error", it)
            }
        )
    }
}
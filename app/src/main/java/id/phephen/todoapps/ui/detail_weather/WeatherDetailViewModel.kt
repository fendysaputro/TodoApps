package id.phephen.todoapps.ui.detail_weather

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.phephen.todoapps.domain.use_case.GetWeatherDetailUseCase
import id.phephen.todoapps.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 * Created by Phephen on 24/07/2022.
 */

class WeatherDetailViewModel @ViewModelInject constructor(
    private val getWeatherDetailUseCase: GetWeatherDetailUseCase
): ViewModel(){

    private val _state = MutableStateFlow<WeatherState>(WeatherState())
    val state = _state.asStateFlow()

    fun getWeatherBy(date: Int) {
        getWeatherDetailUseCase(date).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.emit(WeatherState(weather = result.data))
                }

                is Resource.Error -> {
                    _state.emit(WeatherState(error = result.message ?: "An unexpected error occurred"))
                }
                is Resource.Loading -> {
                    _state.emit(WeatherState(isLoading = true))

                }
            }
        }.launchIn(viewModelScope)
    }
}
package id.phephen.todoapps.ui.weather

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.phephen.todoapps.domain.use_case.GetWeatherListUseCase
import id.phephen.todoapps.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

/**
 * Created by Phephen on 24/07/2022.
 */

class WeatherListViewModel @ViewModelInject constructor(
    private val getWeatherListUseCase: GetWeatherListUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<WeatherListState>(WeatherListState())
    val state = _state.asStateFlow()

    init {
        getWeatherList()
    }

    private fun getWeatherList() {
        getWeatherListUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.emit(WeatherListState(weatherList = result.data ?: emptyList()))
                }
                is Resource.Error -> {
                    _state.emit(WeatherListState(error = result.message ?: "An unexpected error occurred"))
                }
                is Resource.Loading -> {
                    _state.emit(WeatherListState(isLoading = true))
                }
            }
        }.launchIn(viewModelScope)
    }

}
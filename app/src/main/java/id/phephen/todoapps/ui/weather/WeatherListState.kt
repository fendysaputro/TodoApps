package id.phephen.todoapps.ui.weather

import id.phephen.todoapps.data.model.Weather

/**
 * Created by Phephen on 24/07/2022.
 */
data class WeatherListState(
    val isLoading: Boolean = false,
    val weatherList: List<Weather> = emptyList(),
    val error: String = ""
)
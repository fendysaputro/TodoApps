package id.phephen.todoapps.ui.detail_weather

import id.phephen.todoapps.data.model.Weather

/**
 * Created by Phephen on 24/07/2022.
 */

data class WeatherState(
    val isLoading: Boolean = false,
    val weather: Weather? = null,
    val error: String = ""
)
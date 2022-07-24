package id.phephen.todoapps.repository

import id.phephen.todoapps.data.remote.dto.WeatherDto

/**
 * Created by Phephen on 24/07/2022.
 */
interface WeatherRepository {

    suspend fun getWeatherList(): List<WeatherDto>

}
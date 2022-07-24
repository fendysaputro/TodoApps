package id.phephen.todoapps.data.remote.repository

import id.phephen.todoapps.data.remote.OpenWeatherApi
import id.phephen.todoapps.data.remote.dto.WeatherDto
import id.phephen.todoapps.repository.WeatherRepository
import javax.inject.Inject

/**
 * Created by Phephen on 24/07/2022.
 */
class WeatherRepositoryImpl @Inject constructor(
    private val api: OpenWeatherApi
): WeatherRepository {
    override suspend fun getWeatherList(): List<WeatherDto> {
        return api.getCityInfo().list
    }
}
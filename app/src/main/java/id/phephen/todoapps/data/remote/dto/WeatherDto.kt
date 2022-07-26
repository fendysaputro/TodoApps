package id.phephen.todoapps.data.remote.dto

import com.google.gson.annotations.SerializedName
import id.phephen.todoapps.data.model.Weather

/**
 * Created by Phephen on 24/07/2022.
 */

data class WeatherDto(
    val clouds: Int,
    val deg: Int,
    val dt: Int,
    @SerializedName("feels_like")
    val feelsLike: FeelsLikeDto,
    val gust: Double,
    val humidity: Int,
    val pop: Double,
    val pressure: Int,
    val speed: Double,
    val sunrise: Int,
    val sunset: Int,
    val temp: TempDto,
    @SerializedName("weather")
    val weatherInfo: List<WeatherInfoDto>
)

fun WeatherDto.toWeather(): Weather {
    return Weather(
        dt = dt,
        day = temp.day,
        night = temp.night,
        title = weatherInfo.firstOrNull()?.main ?: "",
        description = weatherInfo.firstOrNull()?.description ?: "",
        icon = weatherInfo.firstOrNull()?.icon ?: ""
    )
}

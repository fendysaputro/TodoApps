package id.phephen.todoapps.data.remote.dto

/**
 * Created by Phephen on 24/07/2022.
 */
data class CityInfoDto(
    val city: CityDto,
    val cnt: Int,
    val cod: String,
    val list: List<WeatherDto>,
    val message: Double
)

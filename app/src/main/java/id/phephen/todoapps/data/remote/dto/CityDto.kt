package id.phephen.todoapps.data.remote.dto

/**
 * Created by Phephen on 24/07/2022.
 */
data class CityDto(
    val coord: CoordDto,
    val country: String,
    val id: Int,
    val name: String,
    val population: Int,
    val timezone: Int
)

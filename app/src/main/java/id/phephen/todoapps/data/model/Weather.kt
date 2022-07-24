package id.phephen.todoapps.data.model

/**
 * Created by Phephen on 24/07/2022.
 */
data class Weather(
    val dt: Int,
    val day: Double,
    val night: Double,
    val title: String,
    val description: String,
    val icon: String,
)

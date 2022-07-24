package id.phephen.todoapps.data.remote

import id.phephen.todoapps.data.remote.dto.CityInfoDto
import retrofit2.http.GET

/**
 * Created by Phephen on 24/07/2022.
 */


interface OpenWeatherApi {

    @GET("/data/2.5/forecast/daily?q=Jakarta&mode=json&units=metric&cnt=16&APPID=648a3aac37935e5b45e09727df728ac2")
    suspend fun getCityInfo(): CityInfoDto

}
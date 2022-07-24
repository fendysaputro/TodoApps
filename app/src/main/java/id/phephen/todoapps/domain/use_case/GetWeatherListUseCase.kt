package id.phephen.todoapps.domain.use_case

import id.phephen.todoapps.data.model.Weather
import id.phephen.todoapps.data.remote.dto.toWeather
import id.phephen.todoapps.repository.WeatherRepository
import id.phephen.todoapps.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

/**
 * Created by Phephen on 24/07/2022.
 */
class GetWeatherListUseCase @Inject constructor(
    private val repository: WeatherRepository
) {

    operator fun invoke(): Flow<Resource<List<Weather>>> = flow {
        try {
            emit(Resource.Loading<List<Weather>>())
            val weatherList = repository.getWeatherList().map { it.toWeather() }
            emit(Resource.Success(weatherList))
        } catch (e: HttpException) {
            emit(Resource.Error<List<Weather>>(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error<List<Weather>>("Couldn't reach server. Check your internet connection"))
        }
    }

}
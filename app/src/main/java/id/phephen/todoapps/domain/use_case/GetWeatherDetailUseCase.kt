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
class GetWeatherDetailUseCase @Inject constructor(
    private val repository: WeatherRepository
) {

    operator fun invoke(date: Int): Flow<Resource<Weather>> = flow {
        try {
            emit(Resource.Loading<Weather>())
            val weather = repository.getWeatherList().find { it.dt == date }!!.toWeather()
            emit(Resource.Success(weather))
        } catch (e: HttpException) {
            emit(Resource.Error<Weather>(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error<Weather>("Couldn't reach server. Check your internet connection"))
        }
    }

}
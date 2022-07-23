package id.phephen.todoapps.data.network

import android.content.Context
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

/**
 * Created by Phephen on 23/07/2022.
 */
object Networking {

    fun create(
        baseUrl: String,
        cacheSize: Long,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
    }

    const val BASE_URL = "https://backend.portalsekolah.com/"

}
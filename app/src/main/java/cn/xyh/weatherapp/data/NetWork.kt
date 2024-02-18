package cn.xyh.weatherapp.data

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object NetWork {
    private const val baseUrl = "https://api.open-meteo.com/"
    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    fun <T> createService(clazz: Class<T>): T {
        return retrofit.create(clazz)
    }
}
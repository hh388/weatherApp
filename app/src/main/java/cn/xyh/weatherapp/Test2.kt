package cn.xyh.weatherapp

import cn.xyh.weatherapp.data.mappers.toWeatherDataMap
import cn.xyh.weatherapp.data.mappers.toWeatherInfo
import cn.xyh.weatherapp.data.remote.WeatherApi
import kotlinx.coroutines.runBlocking
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

fun main() {
//    val retrofit = Retrofit.Builder()
//        .baseUrl("https://api.open-meteo.com/")
//        .addConverterFactory(GsonConverterFactory.create())
//        .build()
//
//    val testApi = retrofit.create(TestApi::class.java)
//    runBlocking {
//        val response = testApi.test().execute()
//        println(response.body())
//    }
    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.open-meteo.com/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    val weatherApi = retrofit.create(WeatherApi::class.java)
    runBlocking {
        val data = weatherApi.getWeatherData(0.0, 0.0)
        println(data.toWeatherInfo().toString())
    }
}
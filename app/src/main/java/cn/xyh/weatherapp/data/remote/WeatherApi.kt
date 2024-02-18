package cn.xyh.weatherapp.data.remote

import cn.xyh.weatherapp.data.NetWork
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    // 获取经纬度
    @GET("v1/forecast?hourly=temperature_2m,weathercode,relativehumidity_2m,windspeed_10m,pressure_msl")
    suspend fun getWeatherData(
        @Query("latitude") lat: Double,
        @Query("longitude") long: Double
    ): WeatherDto

    companion object{
        fun instance(): WeatherApi{
            return NetWork.createService(WeatherApi::class.java)
        }
    }
}


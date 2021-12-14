package com.example.theweather.api

import com.example.theweather.model.Weather7Day
import com.example.theweather.model.Currency
import com.example.theweather.model.Currency7Day
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    // https://api.openweathermap.org/data/2.5/weather?q=HaNoi&appid=220dad74c7a8160adea535d772d08adb&units=metric

    @GET("data/2.5/weather")
     fun getWeatherFromApi(
        @Query("q") city: String?,
        @Query("appid") apikey: String?,
        @Query("units") metric: String?

    ): Call<Currency>

    //https://api.weatherbit.io/v2.0/forecast/daily?city=HaNoi&key=69ea3c8ee93e4a55b4a4ea81a456b157&days=7
    @GET("forecast/daily")
    fun getWeatherFromApi7Day(
        @Query("city") city: String?,
        @Query("key") apikey: String?,
        @Query("days") day_number: Int?

    ): Call<Currency7Day>
}
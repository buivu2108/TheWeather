package com.example.theweather.api

import com.example.theweather.model.Content
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object API7Day {
    private val gson = GsonBuilder()
        .setDateFormat(Content.simpleDateValue)
        .create()
    val retrofit: ApiService = Retrofit.Builder()
        .baseUrl("https://api.weatherbit.io/v2.0/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
        .create(ApiService::class.java)
}
package com.example.theweather.model

data class Currency(
    val weather: ArrayList<Weather>,
    val dt: Int, val name: String, val sys: Address,
    val clouds: Cloud, val wind: Wind, val main: Main
)

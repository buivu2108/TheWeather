package com.example.theweather

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.theweather.api.API
import com.example.theweather.model.Content
import com.example.theweather.model.Currency
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class CurrentWeather : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnSearch.setOnClickListener {
            callApi()
        }
        btnNextDay.setOnClickListener {
            val city: String = edtSearch.text.toString().trim()
            var intent = Intent(this@CurrentWeather, FutureWeather::class.java).apply {
                putExtra(Content.keyPutExtra, city)
            }
            startActivity(intent)
        }
    }

    private fun callApi() {
        val city: String = edtSearch.text.toString().trim()
        // https://api.openweathermap.org/data/2.5/weather?q=HaNoi&appid=220dad74c7a8160adea535d772d08adb&units=metric
        val retrofitData =
            API.retrofit.getWeatherFromApi(city, Content.apiKey, Content.metric)
        retrofitData.enqueue(object : Callback<Currency> {
            override fun onResponse(call: Call<Currency>, response: Response<Currency>) {
                val currency = response.body()
                val icon: String = currency?.weather!![0].icon
                val imageUrl = "http://openweathermap.org/img/wn/$icon@2x.png"
                val day: String = currency.dt.toString()
                val l: Long = day.toLong()
                val date = Date(l * 1000L)
                val simpleDateFormat = SimpleDateFormat(Content.simpleDateValue)
                val Date: String = simpleDateFormat.format(date)

                Picasso.get()
                    .load(imageUrl)
                    .into(imgStatus)

                if (currency != null) {
                    tvNameCity.text = currency.name
                    tvNameAddress.text = currency.sys.country
                    tvTemp.text = currency.main.temp.toString() + "Độ C"
                    tvStatus.text = currency.weather[0].description
                    tvHumidity.text = currency.main.humidity.toString() + "%"
                    tvCloud.text = currency.clouds.all.toString() + "%"
                    tvWind.text = currency.wind.speed.toString() + "m/s"
                    tvDateUpdate.text = Date
                }
            }
            override fun onFailure(call: Call<Currency>, t: Throwable) {
                Toast.makeText(this@CurrentWeather, Content.textOnFailure, Toast.LENGTH_SHORT).show()
            }
        })
    }
}
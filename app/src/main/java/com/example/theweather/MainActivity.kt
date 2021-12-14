package com.example.theweather

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.theweather.api.ApiService
import com.example.theweather.model.Currency
import com.google.gson.GsonBuilder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_search.setOnClickListener {
            callApi()
        }
        btn_ngay_tiep_theo.setOnClickListener {
            val city: String = edt_search.text.toString().trim()
            var intent = Intent(this@MainActivity, MainActivity2::class.java).apply {
                putExtra("name_city", city)
            }
            startActivity(intent)
        }
    }

    private fun callApi() {
        val city: String = edt_search.text.toString().trim()

        val gson = GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create()

        val retrofitBuilder = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService::class.java)
        // https://api.openweathermap.org/data/2.5/weather?q=HaNoi&appid=220dad74c7a8160adea535d772d08adb&units=metric
        val retrofitData =
            retrofitBuilder.getWeatherFromApi(city, "220dad74c7a8160adea535d772d08adb", "metric")
        retrofitData.enqueue(object : Callback<Currency> {
            override fun onResponse(call: Call<Currency>, response: Response<Currency>) {
                val currency = response.body()
                val icon: String = currency?.weather!![0].icon
                val imageUrl = "http://openweathermap.org/img/wn/$icon@2x.png"
                val day: String = currency.dt.toString()
                val l: Long = day.toLong()
                val date = Date(l * 1000L)
                val simpleDateFormat = SimpleDateFormat("EEEE yyyy-MM-dd HH:mm:ss")
                val Date: String = simpleDateFormat.format(date)

                Picasso.get()
                    .load(imageUrl)
                    .into(img_trang_thai)

                if (currency != null) {
                    tv_name_city.text = currency.name
                    tv_name_address.text = currency.sys.country
                    tv_nhiet_do.text = currency.main.temp.toString() + "Độ C"
                    tv_trang_thai.text = currency.weather[0].description
                    tv_do_am.text = currency.main.humidity.toString() + "%"
                    tv_may.text = currency.clouds.all.toString() + "%"
                    tv_gio.text = currency.wind.speed.toString() + "m/s"
                    tv_date.text = Date


                }
            }

            override fun onFailure(call: Call<Currency>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Call Api Error", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
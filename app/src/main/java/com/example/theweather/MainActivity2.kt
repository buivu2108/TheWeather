package com.example.theweather

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.theweather.api.ApiService
import com.example.theweather.model.Currency7Day
import com.example.theweather.model.Weather
import com.example.theweather.model.Weather7Day
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main2.*
import kotlinx.android.synthetic.main.item_weather.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import kotlin.collections.ArrayList

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        var intent: Intent = intent
        var city: String? = intent.getStringExtra("name_city")
        Get7DayData()
        initEven()

    }

    private fun initEven() {
        img_btn_back.setOnClickListener {
            onBackPressed()
        }
    }

    private fun Get7DayData() {
        val gson = GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create()

        val retrofitBuilder = Retrofit.Builder()
            .baseUrl("https://api.weatherbit.io/v2.0/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService::class.java)
        //https://api.weatherbit.io/v2.0/forecast/daily?city=HaNoi&key=69ea3c8ee93e4a55b4a4ea81a456b157&days=7
        val intent: Intent = intent
        val city: String? = intent.getStringExtra("name_city")
        val retrofitData2 =
            retrofitBuilder.getWeatherFromApi7Day(city, "69ea3c8ee93e4a55b4a4ea81a456b157", 7)

        retrofitData2.enqueue(object : Callback<Currency7Day> {
            override fun onResponse(call: Call<Currency7Day>, response: Response<Currency7Day>) {
                val currency7day = response.body()

                val recyclerview = findViewById<RecyclerView>(R.id.rcv_7day)
                recyclerview.layoutManager = LinearLayoutManager(this@MainActivity2)
                val dataList = ArrayList<Weather7Day>()
                val adapter = WeatherAdapter(dataList)


                for (i in 0 until 7) {
                    val date: String? = currency7day?.data!![i].valid_date
                    val weather: Weather = currency7day.data[i].weather
                    var minTemp:String = currency7day.data[i].min_temp.toString()
                    var maxTemp:String = currency7day.data[i].max_temp.toString()
                    dataList.add(Weather7Day(date.toString(),weather,maxTemp.toDouble(),minTemp.toDouble()))

                }
                //tv_name_city_2.text = city
                recyclerview.adapter = adapter
            }

            override fun onFailure(call: Call<Currency7Day>, t: Throwable) {
                Toast.makeText(this@MainActivity2, "Call Api Error", Toast.LENGTH_SHORT).show()
            }
        })

    }

}
package com.example.theweather

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.theweather.model.Weather7Day
import kotlinx.android.synthetic.main.item_weather.view.*

class WeatherAdapter(private val dataSet: ArrayList<Weather7Day>) : RecyclerView.Adapter<WeatherAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tv_day: TextView = itemView.findViewById(R.id.tv_ngay_thang)
        val imageWeather: ImageView = itemView.findViewById(R.id.img_weather)

    }
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_weather, viewGroup, false)

        return ViewHolder(view)
    }
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val weather7Day = dataSet[position]
        viewHolder.tv_day.text = weather7Day.valid_date
        viewHolder.itemView.tv_thai_trang.text = weather7Day.weather.description
        viewHolder.itemView.tv_temp_max.text = weather7Day.max_temp.toString()
        viewHolder.itemView.tv_temp_min.text = weather7Day.min_temp.toString()
        viewHolder.imageWeather.setImageResource(R.drawable.cloud)
    }
    override fun getItemCount() = dataSet.size
    }
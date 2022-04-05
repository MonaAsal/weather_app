package com.planradar.cities.ui.cities.cityHistorical

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.planradar.cities.R
import com.planradar.cities.data.CityDetailsRecord
import kotlinx.android.synthetic.main.city_weather_item.view.*

class CitiesHistoricalAdapter(context:Context) : RecyclerView.Adapter<CitiesHistoricalAdapter.ViewHolder>(){

    private var cityHistorical: List<CityDetailsRecord> = arrayListOf()
    var  contextt=context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.city_weather_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = cityHistorical.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(cityHistorical[position],contextt)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n")
        fun bind(cityDetails: CityDetailsRecord, context:Context) {
            itemView.date_time_tv.text = cityDetails.save_date_time
            itemView.weather_degree_tv.text = cityDetails.weather_main+", "+cityDetails.main_temp+ "\u00B0"+" C"
            Glide.with(context).load(cityDetails.weather_icon).into( itemView.weather_iv)
        }
    }
    fun setAllCities(cities: List<CityDetailsRecord>) {
        this.cityHistorical = cities
        notifyDataSetChanged()
    }


}

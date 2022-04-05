package com.planradar.cities.ui.cities.cityDetails

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.planradar.cities.R
import com.planradar.cities.data.CityDetailsRecord
import com.planradar.cities.model.weatherResponse
import com.planradar.cities.utils.Constants
import com.planradar.cities.utils.observe
import kotlinx.android.synthetic.main.activity_city_details.*
import kotlinx.android.synthetic.main.activity_city_details.toolbar
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

class CityDetailsActivity : AppCompatActivity(){

    private lateinit var citiesDetailsViewModel: CitiesDetailsViewModel
    var cityName =""
    var save=false
    private val df = DecimalFormat("#")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city_details)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        citiesDetailsViewModel = ViewModelProviders.of(this).get(CitiesDetailsViewModel::class.java)
        val intent = intent
        if (intent != null && intent.hasExtra(Constants.INTENT_CityName)) {
            cityName = intent.getStringExtra(Constants.INTENT_CityName)
            getData(cityName)
        }
    }

    private fun getData(cityName:String) {
        citiesDetailsViewModel.getCityDetails(cityName,this)
       citiesDetailsViewModel.cityDetailsResponse!!.observe(this, Observer { weatherResponse ->
               showDetails(weatherResponse)
        })
    }

    @SuppressLint("SetTextI18n")
    private fun showDetails(weatherResponse: weatherResponse?) {
        saveCityDetails(weatherResponse)
        df.roundingMode = RoundingMode.CEILING
        city_name_tv.text= weatherResponse!!.name
        city_Description_tv.text= weatherResponse.weather!![0].description
        city_Temperature_tv.text= df.format(getCelsiusValue(weatherResponse.main.temp))+ "\u00B0"+" C"
        city_Humidity_tv.text= weatherResponse.main.humidity.toInt().toString()+" %"
        city_Windspeed_tv.text= df.format(weatherResponse.wind.speed).toString()+" km/h"
        Glide.with(this).load(getImageURL(weatherResponse.weather!![0].icon)).into(city_weather_iv)
    }

    @SuppressLint("SimpleDateFormat")
    private fun saveCityDetails(weatherResponse: weatherResponse?) {
        val id =  null
        val sdf = SimpleDateFormat("dd.MM.yyyy - hh:mm")
        val currentDate = sdf.format(Date())
        val cityDetails = CityDetailsRecord(
            id = id,
            name = cityName,
            weather_main = weatherResponse!!.weather!![0].main,
            weather_icon =getImageURL(weatherResponse.weather!![0].icon),
            main_temp =df.format(getCelsiusValue(weatherResponse.main.temp)).toLong(),
            save_date_time =currentDate, isArchived = 0
        )
        citiesDetailsViewModel.saveCityDetails(cityDetails)
    }

    private fun getImageURL(icon: String?):String {
       return "http://openweathermap.org/img/w/$icon.png"
    }
    private fun getCelsiusValue(kelvinValue:Double):Double{
        return kelvinValue- 273.15
    }
}
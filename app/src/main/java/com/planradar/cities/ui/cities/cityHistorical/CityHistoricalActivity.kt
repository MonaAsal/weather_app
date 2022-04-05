package com.planradar.cities.ui.cities.cityHistorical

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.planradar.cities.R
import com.planradar.cities.utils.Constants
import kotlinx.android.synthetic.main.activity_cities_list.toolbar
import kotlinx.android.synthetic.main.activity_city_historical.*
import kotlinx.android.synthetic.main.content_historical_list.*


class CityHistoricalActivity : AppCompatActivity() {

    private lateinit var cityHistoricalViewModel: CitiesHistoricalViewModel
    private lateinit var cityHistoricalAdapter: CitiesHistoricalAdapter

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city_historical)
        setSupportActionBar(toolbar)

        rv_historical_list.layoutManager = LinearLayoutManager(this)
        cityHistoricalAdapter = CitiesHistoricalAdapter(this)
        rv_historical_list.adapter = cityHistoricalAdapter
        cityHistoricalViewModel = ViewModelProviders.of(this).get(CitiesHistoricalViewModel::class.java)

        val intent = intent
        if (intent != null && intent.hasExtra(Constants.INTENT_CityName)) {
            val cityName = intent.getStringExtra(Constants.INTENT_CityName)
            title_tv.text=cityName+" "+getString(R.string.historical)
            cityHistoricalViewModel.getHistoricalList(cityName).observe(this, Observer {
                cityHistoricalAdapter.setAllCities(it)
            })
        }
    }

}

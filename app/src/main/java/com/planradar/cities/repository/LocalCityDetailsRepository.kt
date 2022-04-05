package com.planradar.cities.repository

import android.app.Application
import android.util.Log.d
import androidx.lifecycle.LiveData
import com.planradar.cities.data.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class LocalCityDetailsRepository(application: Application) {
    private val TAG = "CityDetailsRepository"
    private val cityDetailsDao: CityDetailsDao
    private var historicalList: LiveData<List<CityDetailsRecord>>
    private var cityName :String=""

    init {
        val db = CityDatabase.getInstance(application.applicationContext)
        cityDetailsDao = db!!.cityDetailsDao()
        historicalList = cityDetailsDao.getCityHistorical(cityName)
    }

    fun saveCity(cityDetails: CityDetailsRecord) = runBlocking {
        d(TAG, ":saveCity()")
        this.launch(Dispatchers.IO) {
            cityDetailsDao.saveCityDetails(cityDetails)
        }
    }

    fun getHistoricalList(CityName:String): LiveData<List<CityDetailsRecord>> {
        d(TAG, ":getActiveCities()")
        cityName=CityName
        historicalList = cityDetailsDao.getCityHistorical(cityName)
        return historicalList
    }

}
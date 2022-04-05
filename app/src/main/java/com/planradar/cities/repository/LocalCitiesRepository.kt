package com.planradar.cities.repository

import android.app.Application
import android.util.Log.d
import androidx.lifecycle.LiveData
import com.planradar.cities.data.CityDao
import com.planradar.cities.data.CityDatabase
import com.planradar.cities.data.CityRecord
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class LocalCitiesRepository(application: Application) {
    private val TAG = "CityRepository"
    private val cityDao: CityDao
    private val activeCities: LiveData<List<CityRecord>>

    init {
        val db = CityDatabase.getInstance(application.applicationContext)
        cityDao = db!!.cityDao()
        activeCities = cityDao.getCities()
    }

    fun saveCity(city: CityRecord) = runBlocking {
        d(TAG, ":saveCity()")
        this.launch(Dispatchers.IO) {
            cityDao.saveCity(city)
        }
    }



    fun deleteCity(city: CityRecord) = runBlocking {
        d(TAG, ":deleteCity()")
        this.launch(Dispatchers.IO) {
            cityDao.deleteCity(city)
        }
    }

    fun getActiveCities(): LiveData<List<CityRecord>> {
        d(TAG, ":getActiveCities()")
        return activeCities
    }


}
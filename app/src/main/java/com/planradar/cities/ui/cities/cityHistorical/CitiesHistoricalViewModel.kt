package com.planradar.cities.ui.cities.cityHistorical

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.planradar.cities.data.CityDetailsRecord
import com.planradar.cities.repository.LocalCityDetailsRepository

class CitiesHistoricalViewModel(application: Application) : AndroidViewModel(application) {

    private val repo: LocalCityDetailsRepository = LocalCityDetailsRepository(application)

    fun getHistoricalList(cityName:String): LiveData<List<CityDetailsRecord>> {
        return repo.getHistoricalList(cityName)
    }

}
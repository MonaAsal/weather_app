package com.planradar.cities.ui.cities.citiesList

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.planradar.cities.data.CityRecord
import com.planradar.cities.repository.LocalCitiesRepository

class CitiesViewModel(application: Application) : AndroidViewModel(application) {

    private val repo: LocalCitiesRepository = LocalCitiesRepository(application)

    fun saveCity(city: CityRecord) {
        repo.saveCity(city)
    }

    fun updateCity(city: CityRecord) {
        repo.deleteCity(city)
    }

    fun getCitiesList(): LiveData<List<CityRecord>> {
        return repo.getActiveCities()
    }

    fun deleteCity(city: CityRecord) {
        city.isArchived = 1
        repo.deleteCity(city)
    }

}
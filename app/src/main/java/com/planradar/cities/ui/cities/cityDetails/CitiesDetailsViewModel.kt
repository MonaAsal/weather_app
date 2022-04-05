package com.planradar.cities.ui.cities.cityDetails

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.planradar.cities.data.CityDetailsRecord
import com.planradar.cities.model.weatherResponse
import com.planradar.cities.repository.CitiesRemoteRepository
import com.planradar.cities.repository.LocalCityDetailsRepository

class CitiesDetailsViewModel(application: Application) : AndroidViewModel(application) {

     var cityDetailsResponse: MutableLiveData<weatherResponse>? = null
    private val repo: LocalCityDetailsRepository = LocalCityDetailsRepository(application)


    fun getCityDetails(CityName:String,context:Context) : LiveData<weatherResponse>? {
         cityDetailsResponse = CitiesRemoteRepository.getCityDetails(CityName,context)
        return cityDetailsResponse
    }

    fun saveCityDetails(cityDetails: CityDetailsRecord) {
        repo.saveCity(cityDetails)
    }

}
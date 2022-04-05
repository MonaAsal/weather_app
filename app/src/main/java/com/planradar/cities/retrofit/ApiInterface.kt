package com.planradar.cities.retrofit

import com.planradar.cities.model.weatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("data/2.5/weather")
    fun getServices(@Query("q") CityName: String?, @Query("appid") Appid: String?): Call<Any>
}
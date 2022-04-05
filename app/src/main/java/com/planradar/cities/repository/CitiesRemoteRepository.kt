package com.planradar.cities.repository

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.planradar.cities.model.ErrorResponse
import com.planradar.cities.model.weatherResponse
import com.planradar.cities.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object CitiesRemoteRepository {

    val CityDetailsResponse = MutableLiveData<weatherResponse>()
    fun getCityDetails(CityName:String,context:Context): MutableLiveData<weatherResponse> {
         val call:Call<Any> = RetrofitClient.apiInterface.getServices(CityName,"f5cb0b965ea1564c50c6f1b74534d823")
         call.enqueue(object: Callback<Any>{
             override fun onResponse(call: Call<Any>, response: Response<Any>) {
                 if (response.isSuccessful) {
                     val gson = Gson()
                     val cityData = gson.fromJson(gson.toJson(response.body()), weatherResponse::class.java)
                         CityDetailsResponse.value = weatherResponse(cityData.weather, cityData.main, cityData.wind, cityData.cod,cityData.name)
                 }
                 else {
                     val gson = Gson()
                     val errorBody = response.errorBody() ?: return
                     val type = object : TypeToken<ErrorResponse>() {}.type
                     val errorResponse: ErrorResponse? = gson.fromJson(errorBody.charStream(), type)
                     val errorMessage = errorResponse!!.message
                     Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
                 }
             }
             override fun onFailure(call: Call<Any>, t: Throwable) {
                 val errorMessage = t.message.toString()
                 Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
             }
         })
        return CityDetailsResponse
    }
}
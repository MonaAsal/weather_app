package com.planradar.cities.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CityDetailsDao {
    @Query("SELECT * FROM CityDetails WHERE name=:CityName ORDER BY id ASC")
    fun getCityHistorical(CityName:String): LiveData<List<CityDetailsRecord>>

    @Insert
    suspend fun saveCityDetails(cityDetails: CityDetailsRecord)

    @Delete
    suspend fun deleteCityDetails(cityDetails: CityDetailsRecord)


    @Query("DELETE FROM CityDetails WHERE name=:CityName")
    fun deleteCityHistorical(CityName: String)

}
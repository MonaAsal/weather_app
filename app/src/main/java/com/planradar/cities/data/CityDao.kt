package com.planradar.cities.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CityDao {
    @Query("SELECT * FROM Cities WHERE isArchived=0 ORDER BY id ASC")
    fun getCities(): LiveData<List<CityRecord>>

    @Insert
    suspend fun saveCity(city: CityRecord)

    @Delete
    suspend fun deleteCity(city: CityRecord)

}
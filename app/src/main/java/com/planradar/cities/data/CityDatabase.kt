package com.planradar.cities.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CityRecord::class ,CityDetailsRecord::class], version = 6, exportSchema = false)
abstract class CityDatabase : RoomDatabase() {

    abstract fun cityDao(): CityDao
    abstract fun cityDetailsDao(): CityDetailsDao

    companion object {
        private var INSTANCE: CityDatabase? = null

        fun getInstance(context: Context): CityDatabase? {
            if (INSTANCE == null) {
                synchronized(CityDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context,
                        CityDatabase::class.java,
                        "city_db"
                    ).fallbackToDestructiveMigration().build()
                }
            }
            return INSTANCE
        }
    }
}
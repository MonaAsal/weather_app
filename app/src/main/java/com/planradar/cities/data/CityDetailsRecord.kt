package com.planradar.cities.data

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CityDetails")
data class CityDetailsRecord(
    @PrimaryKey(autoGenerate = true) val id: Long?,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "weather_main") val weather_main: String?,
    @ColumnInfo(name = "weather_icon") val weather_icon: String?,
    @ColumnInfo(name = "main_temp") val main_temp: Long?,
    @ColumnInfo(name = "save_date_time") val save_date_time: String?,
    @ColumnInfo(name = "isArchived") var isArchived: Int? = 0
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Long::class.java.classLoader) as? Long,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Long::class.java.classLoader) as? Long,
        parcel.readString() ,
        isArchived = parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(name)
        parcel.writeValue(weather_main)
        parcel.writeValue(weather_icon)
        parcel.writeValue(main_temp)
        parcel.writeValue(save_date_time)
        parcel.writeInt(isArchived!!)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CityDetailsRecord> {
        override fun createFromParcel(parcel: Parcel): CityDetailsRecord {
            return CityDetailsRecord(parcel)
        }

        override fun newArray(size: Int): Array<CityDetailsRecord?> {
            return arrayOfNulls(size)
        }
    }
}
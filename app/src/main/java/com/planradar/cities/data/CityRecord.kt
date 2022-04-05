package com.planradar.cities.data

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Cities")
data class CityRecord(
    @PrimaryKey(autoGenerate = true) val id: Long?,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "isArchived") var isArchived: Int? = 0  // 0 - false, 1 - true
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Long::class.java.classLoader) as? Long,
        parcel.readString(),
        isArchived = parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(title)
        parcel.writeInt(isArchived!!)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CityRecord> {
        override fun createFromParcel(parcel: Parcel): CityRecord {
            return CityRecord(parcel)
        }

        override fun newArray(size: Int): Array<CityRecord?> {
            return arrayOfNulls(size)
        }
    }
}
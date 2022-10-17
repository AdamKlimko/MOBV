package com.example.mobv.model

import android.os.Parcel
import android.os.Parcelable

data class Pub(
    val type: String?,
    val id: Long,
    val lat: Float,
    val lon: Float,
    val tags: Tags?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readLong(),
        parcel.readFloat(),
        parcel.readFloat(),
        parcel.readParcelable(Tags::class.java.classLoader)
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(type)
        parcel.writeLong(id)
        parcel.writeFloat(lat)
        parcel.writeFloat(lon)
        parcel.writeParcelable(tags, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Pub> {
        override fun createFromParcel(parcel: Parcel): Pub {
            return Pub(parcel)
        }

        override fun newArray(size: Int): Array<Pub?> {
            return arrayOfNulls(size)
        }
    }
}

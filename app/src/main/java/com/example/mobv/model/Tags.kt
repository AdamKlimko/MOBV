package com.example.mobv.model

import android.os.Parcel
import android.os.Parcelable

data class Tags(
    val name: String? = "'Name not found'"
) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Tags> {
        override fun createFromParcel(parcel: Parcel): Tags {
            return Tags(parcel)
        }

        override fun newArray(size: Int): Array<Tags?> {
            return arrayOfNulls(size)
        }
    }
}

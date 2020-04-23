package com.example.findhospital.model

import android.os.Parcel
import android.os.Parcelable

class Hospital(hName: String, hNumber: String, hAddress: String, hSubject: String) : Parcelable {
    val hName = hName

    val hNumber = hNumber

    val hAddress = hAddress

    val hSubject = hSubject

    override fun toString(): String {
        return hName + " / " + hNumber + " / " + hAddress
    }

    constructor(source: Parcel) : this(
        source.readString().toString(),
        source.readString().toString(),
        source.readString().toString(),
        source.readString().toString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(hName)
        writeString(hNumber)
        writeString(hAddress)
        writeString(hSubject)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Hospital> = object : Parcelable.Creator<Hospital> {
            override fun createFromParcel(source: Parcel): Hospital = Hospital(source)
            override fun newArray(size: Int): Array<Hospital?> = arrayOfNulls(size)
        }
    }
}
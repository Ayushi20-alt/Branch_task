package com.example.branchtask.domain.models

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

data class HomemodelItem(
    val agent_id: String,
    val body: String,
    var id: Int,
    val thread_id: String,
    val timestamp: String,
    val user_id: String
): Parcelable {
    // Parcelable constructor
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    // Write to parcel
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(agent_id)
        parcel.writeString(body)
        parcel.writeInt(id)
        parcel.writeString(thread_id)
        parcel.writeString(timestamp)
        parcel.writeString(user_id)
    }

    // Describe contents, not used here
    override fun describeContents(): Int {
        return 0
    }

    // Companion object for Parcelable CREATOR
    companion object CREATOR : Parcelable.Creator<HomemodelItem> {
        override fun createFromParcel(parcel: Parcel): HomemodelItem {
            return HomemodelItem(parcel)
        }

        override fun newArray(size: Int): Array<HomemodelItem?> {
            return arrayOfNulls(size)
        }
    }
}
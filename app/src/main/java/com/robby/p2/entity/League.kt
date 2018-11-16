package com.robby.p2.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class League(@SerializedName("idLeague") val id: String, @SerializedName("strLeague") val name: String) : Parcelable {

    override fun toString(): String {
        return name
    }
}
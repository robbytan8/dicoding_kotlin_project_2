package com.robby.p2.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Club(@SerializedName("strTeam") val name: String, @SerializedName("strTeamBadge") val logo: String, @SerializedName("strDescriptionEN") val description: String) : Parcelable
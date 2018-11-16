package com.robby.p2.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Event(
    @SerializedName("idHomeTeam") val idHomeTeam: String ?= null,
    @SerializedName("idAwayTeam") val idAwayTeam: String ?= null,
    @SerializedName("strHomeTeam") val homeTeam: String ?= null,
    @SerializedName("strAwayTeam") val awayTeam: String ?= null,
    @SerializedName("intHomeScore") val homeScore: String ?= null,
    @SerializedName("intAwayScore") val awayScore: String ?= null,
    @SerializedName("strHomeGoalDetails") val homeGoalDetails: String ?= null,
    @SerializedName("strHomeRedCards") val homeRedCards: String ?= null,
    @SerializedName("strHomeYellowCards") val homeYellowCards: String ?= null,
    @SerializedName("strHomeLineupGoalkeeper") val homeLineGk: String ?= null,
    @SerializedName("strHomeLineupDefense") val homeLineDef: String ?= null,
    @SerializedName("strHomeLineupMidfield") val homeLineMid: String ?= null,
    @SerializedName("strHomeLineupForward") val homeLineFor: String ?= null,
    @SerializedName("strHomeLineupSubstitutes") val homeSubs: String ?= null,
    @SerializedName("strAwayGoalDetails") val awayGoalDetails: String ?= null,
    @SerializedName("strAwayRedCards") val awayRedCards: String ?= null,
    @SerializedName("strAwayYellowCards") val awayYellowCards: String ?= null,
    @SerializedName("strAwayLineupGoalkeeper") val awayLineGk: String ?= null,
    @SerializedName("strAwayLineupDefense") val awayLineDef: String ?= null,
    @SerializedName("strAwayLineupMidfield") val awayLineMid: String ?= null,
    @SerializedName("strAwayLineupForward") val awayLineFor: String ?= null,
    @SerializedName("strAwayLineupSubstitutes") val awaySubs: String ?= null,
    @SerializedName("dateEvent") val dateEvent: String ?= null
    ) : Parcelable
package com.yuchen.kkbox.data

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Track(
    val id:String = "No id data",
    val name: String = "No name data",
    val duration: Long = 0L,
    val isrc: String = "No isrc data",
    val url: String = "No url data",
    @Json(name = "track_number")val trackNumber: Int = 0,
    val explicitness: Boolean = true,
    @Json(name = "available_territories") val availableTerritories: List<String> = listOf(),
    val album: Album? = null
) : Parcelable

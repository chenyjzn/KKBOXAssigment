package com.yuchen.kkbox.data

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NewRelease(
    val id: String,
    val name: String,
    val url: String,
    val explicitness : Boolean,
    @Json(name = "available_territories") val availableTerritories:List<String>,
    @Json(name = "release_date") val releaseDate: String,
    val images: List<Image>,
    val artist: Artist
) : Parcelable
package com.yuchen.kkbox.data

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Track(
    val name: String,
    val duration: Long,
    val isrc: String,
    val url: String,
    val track_number: Int,
    val explicitness: Boolean,
    @Json(name = "availableTerritories") val available_territories: List<String>
) : Parcelable

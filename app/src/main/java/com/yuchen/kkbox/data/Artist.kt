package com.yuchen.kkbox.data

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Artist(
    val id: String,
    val name: String,
    val url: String,
    val images: List<Image>
) : Parcelable
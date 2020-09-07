package com.yuchen.kkbox.data

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Album(
    val id: String,
    val title: String,
    val description: String,
    val url: String,
    val images: List<Image>,
    @Json(name = "updated_at") val updatedAt: String,
    val owner: Owner
) : Parcelable
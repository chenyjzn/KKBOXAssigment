package com.yuchen.kkbox.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Owner(
    val id: String,
    val url: String,
    val name: String,
    val description: String,
    val images: List<Image>
) : Parcelable
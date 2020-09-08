package com.yuchen.kkbox.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Artist(
    val id: String = "No id data",
    val name: String = "No name data",
    val url: String = "No url data",
    val images: List<Image> = listOf()
) : Parcelable
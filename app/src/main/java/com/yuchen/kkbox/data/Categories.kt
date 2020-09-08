package com.yuchen.kkbox.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Categories(
    val id: String = "No id data",
    val title: String = "No title data",
    val albums: AlbumsResult? = null
) : Parcelable
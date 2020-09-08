package com.yuchen.kkbox.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AlbumsResult(
    val greeting: String = "No greeting data",
    val data: List<Album> = listOf(),
    val paging: Paging? = null,
    val summary: Summary? = null
) : Parcelable
package com.yuchen.kkbox.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AlbumsResult(
    val data: List<Album>,
    val paging: Paging,
    val summary: Summary
) : Parcelable
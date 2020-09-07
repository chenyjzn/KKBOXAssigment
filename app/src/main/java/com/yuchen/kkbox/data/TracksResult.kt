package com.yuchen.kkbox.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TracksResult(
    val data: List<Track>,
    val paging: Paging,
    val summary: Summary
) : Parcelable
package com.yuchen.kkbox.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Result(
    val data: List<Data>,
    val paging: Paging,
    val summary: Summary
) : Parcelable
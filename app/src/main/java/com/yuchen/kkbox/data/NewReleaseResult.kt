package com.yuchen.kkbox.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NewReleaseResult(
    val data: List<NewRelease>,
    val paging: Paging,
    val summary: Summary
) : Parcelable
package com.yuchen.kkbox.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Paging(
    val offse: Long = 0L,
    val limit: Long = 0L,
    val previous: String? = null,
    val next: String? = null
) : Parcelable
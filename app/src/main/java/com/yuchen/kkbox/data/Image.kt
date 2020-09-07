package com.yuchen.kkbox.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Image(
    val height: Long,
    val width: Long,
    val url: String
) : Parcelable
package com.yuchen.kkbox.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CategoriesResult(
    val data: List<Categories>,
    val paging: Paging,
    val summary: Summary
) : Parcelable
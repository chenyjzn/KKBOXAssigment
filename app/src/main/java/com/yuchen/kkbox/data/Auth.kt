package com.yuchen.kkbox.data

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Auth(
    @Json(name = "access_token") val accessToken: String = "access_token",
    @Json(name = "token_type") val tokenType: String = "token_type",
    @Json(name = "expires_in") val expiresIn: Long = 0
) : Parcelable
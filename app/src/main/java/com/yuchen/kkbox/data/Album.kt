package com.yuchen.kkbox.data

import android.os.Parcelable
import com.squareup.moshi.Json
import com.yuchen.kkbox.time.convertReleaseDateToyyyyMMdd
import com.yuchen.kkbox.time.convertUpdateAtToyyyyMMdd
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Album(
    //Both
    val id: String = "",
    val url: String = "",
    val images: List<Image> = listOf(),
    //For rank & featured
    val title: String? = null,
    val description: String = "",
    @Json(name = "updated_at") val updatedAt: String? = null,
    val owner: Owner? = null,
    //for new release
    val name: String? = null,
    val explicitness: Boolean = true,
    @Json(name = "available_territories") val availableTerritories: List<String> = listOf(),
    @Json(name = "release_date") val releaseDate: String? = null,
    val artist: Artist? = null
) : Parcelable {
    val displayTitle: String
        get() = title ?: (name ?: "No title info")

    val displayDate: String
        get() = when {
            updatedAt != null -> {
                convertUpdateAtToyyyyMMdd(updatedAt) ?: "No Date info"
            }
            releaseDate != null -> {
                convertReleaseDateToyyyyMMdd(releaseDate) ?: "No Date info"
            }
            else -> {
                "No Date info"
            }
        }

    val displayArtist: String
        get() = owner?.name ?: (artist?.name ?: "No Artist info")

    val displayCover: String?
        get() = if (images.isEmpty()) {
            null
        } else {
            images[images.lastIndex].url
        }
}
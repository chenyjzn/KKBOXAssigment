package com.yuchen.kkbox.data

sealed class DataItem {
    data class Title(val title: String) : DataItem()
    data class NewReleaseList(val albums: List<Album>) : DataItem()
    data class Featured(val album: Album) : DataItem()
    data class Rank(val album: Album) : DataItem()

    companion object{
        const val TITLE = 0
        const val NEW_RELEASE_LIST = 1
        const val FEATURED = 2
        const val RANK = 3
    }
}
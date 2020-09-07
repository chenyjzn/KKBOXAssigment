package com.yuchen.kkbox.network

sealed class LoadApiStatus {
    data class ERROR(val message: String) : LoadApiStatus()
    object LOADING : LoadApiStatus()
    object DONE : LoadApiStatus()
}
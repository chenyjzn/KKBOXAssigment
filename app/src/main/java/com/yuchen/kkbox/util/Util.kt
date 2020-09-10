package com.yuchen.kkbox.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.yuchen.kkbox.KkboxApplication

object Util {
    fun isInternetConnected(): Boolean {
        val cm = KkboxApplication.instance
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting == true
    }
}

fun getString(resourceId: Int): String {
    return KkboxApplication.instance.getString(resourceId)
}
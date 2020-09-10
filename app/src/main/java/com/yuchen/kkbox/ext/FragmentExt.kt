package com.yuchen.kkbox.ext

import androidx.fragment.app.Fragment
import com.yuchen.kkbox.KkboxApplication
import com.yuchen.kkbox.data.Album
import com.yuchen.kkbox.data.Auth
import com.yuchen.kkbox.factory.ViewModelAuthAlbumFactory
import com.yuchen.kkbox.factory.ViewModelAuthFactory

fun Fragment.getVmFactory(auth: Auth): ViewModelAuthFactory {
    val repository = (requireContext().applicationContext as KkboxApplication).kkboxRepository
    return ViewModelAuthFactory(repository,auth)
}

fun Fragment.getVmFactory(auth: Auth,album: Album): ViewModelAuthAlbumFactory {
    val repository = (requireContext().applicationContext as KkboxApplication).kkboxRepository
    return ViewModelAuthAlbumFactory(repository,auth,album)
}
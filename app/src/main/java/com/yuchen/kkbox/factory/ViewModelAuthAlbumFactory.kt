package com.yuchen.kkbox.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yuchen.kkbox.data.Album
import com.yuchen.kkbox.data.Auth
import com.yuchen.kkbox.tracks.TracksViewModel

class ViewModelAuthAlbumFactory constructor(
    private val auth: Auth,
    private val album:Album
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        with(modelClass) {
            when {
                isAssignableFrom(TracksViewModel::class.java) ->
                    TracksViewModel(auth,album)
                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}
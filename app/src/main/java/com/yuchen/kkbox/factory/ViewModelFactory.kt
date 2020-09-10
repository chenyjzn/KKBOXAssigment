package com.yuchen.kkbox.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yuchen.kkbox.AuthViewModel
import com.yuchen.kkbox.MainViewModel
import com.yuchen.kkbox.data.source.KkboxRepository

class ViewModelFactory constructor(
    private val repository: KkboxRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        with(modelClass) {
            when {
                isAssignableFrom(MainViewModel::class.java) ->
                    MainViewModel(repository)
                isAssignableFrom(AuthViewModel::class.java) ->
                    AuthViewModel(repository)
                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}

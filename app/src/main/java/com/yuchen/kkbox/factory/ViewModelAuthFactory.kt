package com.yuchen.kkbox.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yuchen.kkbox.data.Auth
import com.yuchen.kkbox.data.source.KkboxRepository
import com.yuchen.kkbox.new.NewViewModel
import com.yuchen.kkbox.rank.RankViewModel

class ViewModelAuthFactory constructor(
    private val repository: KkboxRepository,
    private val auth: Auth
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        with(modelClass) {
            when {
                isAssignableFrom(NewViewModel::class.java) ->
                    NewViewModel(repository,auth)
                isAssignableFrom(RankViewModel::class.java) ->
                    RankViewModel(repository,auth)
                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}
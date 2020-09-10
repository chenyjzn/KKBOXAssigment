package com.yuchen.kkbox.ext

import android.app.Activity
import com.yuchen.kkbox.KkboxApplication
import com.yuchen.kkbox.factory.ViewModelFactory

fun Activity.getVmFactory():ViewModelFactory{
    val repository = (applicationContext as KkboxApplication).kkboxRepository
    return ViewModelFactory(repository)
}
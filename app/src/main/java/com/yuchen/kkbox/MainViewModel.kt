package com.yuchen.kkbox

import androidx.lifecycle.ViewModel
import com.yuchen.kkbox.data.source.KkboxRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class MainViewModel (private val kkboxRepository: KkboxRepository): ViewModel(){
    private val viewModelJob= Job()
    private val coroutineScope= CoroutineScope(viewModelJob+Dispatchers.Main)
}

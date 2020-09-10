package com.yuchen.kkbox

import android.util.Log
import androidx.lifecycle.ViewModel
import com.yuchen.kkbox.data.source.KkboxRepository
import com.yuchen.kkbox.network.KkboxAuthApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainViewModel (private val kkboxRepository: KkboxRepository): ViewModel(){
    private val viewModelJob= Job()
    private val coroutineScope= CoroutineScope(viewModelJob+Dispatchers.Main)
}

package com.yuchen.kkbox.rank

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yuchen.kkbox.TERRITORY
import com.yuchen.kkbox.data.Auth
import com.yuchen.kkbox.data.AlbumsResult
import com.yuchen.kkbox.network.KkboxApi
import com.yuchen.kkbox.network.LoadApiStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class RankViewModel(private val auth: Auth) : ViewModel() {

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _rankResult = MutableLiveData<AlbumsResult>()
    val rankResult: LiveData<AlbumsResult>
        get() = _rankResult

    private val _loadApiStatus = MutableLiveData<LoadApiStatus>()
    val loadApiStatus: LiveData<LoadApiStatus>
        get() = _loadApiStatus

    private fun getRankList(){
        coroutineScope.launch {
            val rankResultDeferred = KkboxApi.kkboxApiService.getRankList(
                "${auth.tokenType} ${auth.accessToken}", TERRITORY
            )
            try {
                _loadApiStatus.value = LoadApiStatus.LOADING
                val rankResult=rankResultDeferred.await()
                _loadApiStatus.value = LoadApiStatus.DONE
                _rankResult.value = rankResult
            }catch (t:Throwable){
                _loadApiStatus.value = LoadApiStatus.ERROR(t.toString())
                _loadApiStatus.value = LoadApiStatus.DONE
            }
        }
    }

    init {
        getRankList()
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
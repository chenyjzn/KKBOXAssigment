package com.yuchen.kkbox.rank

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yuchen.kkbox.LIMIT
import com.yuchen.kkbox.TERRITORY
import com.yuchen.kkbox.data.Auth
import com.yuchen.kkbox.data.Result
import com.yuchen.kkbox.network.KkboxApi
import com.yuchen.kkbox.network.LoadApiStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class RankViewModel(private val auth: Auth) : ViewModel() {

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _featureResult = MutableLiveData<Result>()
    val result: LiveData<Result>
        get() = _featureResult

    private val _loadApiStatus = MutableLiveData<LoadApiStatus>()
    val loadApiStatus: LiveData<LoadApiStatus>
        get() = _loadApiStatus

    private fun getRankList(){
        coroutineScope.launch {
            val featureResultDeferred = KkboxApi.kkboxApiService.getRankList(
                "${auth.tokenType} ${auth.accessToken}", TERRITORY
            )
            try {
                _loadApiStatus.value = LoadApiStatus.LOADING
                val featureResult=featureResultDeferred.await()
                _loadApiStatus.value = LoadApiStatus.DONE
                _featureResult.value = featureResult
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
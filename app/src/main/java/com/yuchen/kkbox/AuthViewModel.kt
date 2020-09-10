package com.yuchen.kkbox

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yuchen.kkbox.data.Auth
import com.yuchen.kkbox.data.source.KkboxRepository
import com.yuchen.kkbox.network.KkboxAuthApi
import com.yuchen.kkbox.network.LoadApiStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class AuthViewModel(private val kkboxRepository: KkboxRepository): ViewModel() {
    private val viewModelJob= Job()
    private val coroutineScope= CoroutineScope(viewModelJob+ Dispatchers.Main)

    private val grant_type = "client_credentials"
    private val client_id = "648c841bd9ff30e3b909be0aab0141e9"
    private val client_secret = "ac334aab260eb1552122ba4cef5fa7d7"

    private val _auth = MutableLiveData<Auth>()
    val auth: LiveData<Auth>
        get() = _auth

    private val _loadApiStatus = MutableLiveData<LoadApiStatus>()
    val loadApiStatus: LiveData<LoadApiStatus>
        get() = _loadApiStatus

    init {
        getAuth()
    }

    private fun getAuth() {
        coroutineScope.launch {
            val authResultDeferred = KkboxAuthApi.kkboxAuthApiService.getKKBOXAuth(grant_type,client_id,client_secret)
            try {
                _loadApiStatus.value = LoadApiStatus.LOADING
                val authResult=authResultDeferred.await()
                _loadApiStatus.value = LoadApiStatus.DONE
                _auth.value = authResult
            }catch (t:Throwable){
                _loadApiStatus.value = LoadApiStatus.ERROR(t.toString())
                _loadApiStatus.value = LoadApiStatus.DONE
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
package com.yuchen.kkbox

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yuchen.kkbox.data.Auth
import com.yuchen.kkbox.data.RepoResult
import com.yuchen.kkbox.data.source.KkboxRepository
import com.yuchen.kkbox.network.LoadApiStatus
import com.yuchen.kkbox.util.getString
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class AuthViewModel(private val kkboxRepository: KkboxRepository): ViewModel() {
    private val viewModelJob= Job()
    private val coroutineScope= CoroutineScope(viewModelJob+ Dispatchers.Main)

    private val GRANT_TYPE = "client_credentials"
    private val CLIENT_ID = "648c841bd9ff30e3b909be0aab0141e9"
    private val CLIENT_SECRET = "ac334aab260eb1552122ba4cef5fa7d7"

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
            _loadApiStatus.value = LoadApiStatus.LOADING
            val result = kkboxRepository.getKKBOXAuth(GRANT_TYPE,CLIENT_ID,CLIENT_SECRET)
            when(result){
                is RepoResult.Success -> {
                    _auth.value = result.data
                }
                is RepoResult.Err -> {
                    _loadApiStatus.value = LoadApiStatus.ERROR(result.error)
                }
                is RepoResult.Except -> {
                    _loadApiStatus.value = LoadApiStatus.ERROR(result.exception.toString())
                }
            }
            _loadApiStatus.value = LoadApiStatus.DONE
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
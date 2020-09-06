package com.yuchen.kkbox

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yuchen.kkbox.data.Auth
import com.yuchen.kkbox.network.KkboxApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainViewModel (): ViewModel(){
    private val viewModelJob= Job()
    private val coroutineScope= CoroutineScope(viewModelJob+Dispatchers.Main)

    private val grant_type = "client_credentials"
    private val client_id = "648c841bd9ff30e3b909be0aab0141e9"
    private val client_secret = "ac334aab260eb1552122ba4cef5fa7d7"

    private val _auth = MutableLiveData<Auth>()
    val auth: LiveData<Auth>
        get() = _auth

    init {
        getAuth()
    }

    private fun getAuth() {
        coroutineScope.launch {
            val authResultDeferred = KkboxApi.kkboxApiService.getKKBOXAuth(grant_type,client_id,client_secret)
            try {
                val authResult=authResultDeferred.await()
                _auth.value = authResult
            }catch (t:Throwable){
                Log.d("chenyjzn","$t")
            }
        }
    }
}

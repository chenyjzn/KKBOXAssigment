package com.yuchen.kkbox.tracks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yuchen.kkbox.LIMIT_500
import com.yuchen.kkbox.OFFSET_0
import com.yuchen.kkbox.TERRITORY
import com.yuchen.kkbox.data.*
import com.yuchen.kkbox.network.KkboxApi
import com.yuchen.kkbox.network.LoadApiStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class TracksViewModel(private val auth: Auth,val album: Album) : ViewModel() {

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _tracksResult = MutableLiveData<TracksResult>()
    val tracksResult: LiveData<TracksResult>
        get() = _tracksResult

    private val _loadApiStatus = MutableLiveData<LoadApiStatus>()
    val loadApiStatus: LiveData<LoadApiStatus>
        get() = _loadApiStatus

    private val _navigateToTrack = MutableLiveData<Track>()
    val navigateToTrack: LiveData<Track>
        get() = _navigateToTrack

    private fun getTracksList(){
        coroutineScope.launch {
            val tracksResultDeferred = KkboxApi.kkboxApiService.getTracksByAlbum(
                album.id, "${auth.tokenType} ${auth.accessToken}", TERRITORY, OFFSET_0, LIMIT_500
            )
            try {
                _loadApiStatus.value = LoadApiStatus.LOADING
                val tracksResult=tracksResultDeferred.await()
                _loadApiStatus.value = LoadApiStatus.DONE
                _tracksResult.value = tracksResult
            }catch (t:Throwable){
                _loadApiStatus.value = LoadApiStatus.ERROR(t.toString())
                _loadApiStatus.value = LoadApiStatus.DONE
            }
        }
    }

    fun setNavigateToTrack(track: Track){
        _navigateToTrack.value = track
    }

    fun navigateToTracksDone(){
        _navigateToTrack.value = null
    }

    init {
        getTracksList()
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
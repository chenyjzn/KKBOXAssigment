package com.yuchen.kkbox.tracks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yuchen.kkbox.LIMIT_500
import com.yuchen.kkbox.OFFSET_0
import com.yuchen.kkbox.TERRITORY
import com.yuchen.kkbox.data.*
import com.yuchen.kkbox.data.source.KkboxRepository
import com.yuchen.kkbox.network.LoadApiStatus
import kotlinx.coroutines.*

class TracksViewModel(private val kkboxRepository: KkboxRepository, private val auth: Auth, val album: Album) : ViewModel() {

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
            _loadApiStatus.value = LoadApiStatus.LOADING
            val result =  if (album.isFromNewRelease()) {
                kkboxRepository.getTracksByAlbum(album,auth, TERRITORY, OFFSET_0, LIMIT_500)
            }else{
                kkboxRepository.getTracksByChart(album,auth, TERRITORY, OFFSET_0, LIMIT_500)
            }
            when(result){
                is RepoResult.Success -> {
                    _tracksResult.value = result.data
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
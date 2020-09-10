package com.yuchen.kkbox.rank

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yuchen.kkbox.TERRITORY
import com.yuchen.kkbox.data.Album
import com.yuchen.kkbox.data.Auth
import com.yuchen.kkbox.data.AlbumsResult
import com.yuchen.kkbox.data.RepoResult
import com.yuchen.kkbox.data.source.KkboxRepository
import com.yuchen.kkbox.network.KkboxApi
import com.yuchen.kkbox.network.LoadApiStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class RankViewModel(private val kkboxRepository: KkboxRepository, private val auth: Auth) : ViewModel() {

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _rankResult = MutableLiveData<AlbumsResult>()
    val rankResult: LiveData<AlbumsResult>
        get() = _rankResult

    private val _loadApiStatus = MutableLiveData<LoadApiStatus>()
    val loadApiStatus: LiveData<LoadApiStatus>
        get() = _loadApiStatus

    private val _navigateToTracks = MutableLiveData<Album>()
    val navigateToTracks: LiveData<Album>
        get() = _navigateToTracks

    private fun getRankList(){
        coroutineScope.launch {
            _loadApiStatus.value = LoadApiStatus.LOADING
            val result = kkboxRepository.getRankList(
                auth, TERRITORY
            )
            when(result){
                is RepoResult.Success -> {
                    _rankResult.value = result.data
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

    fun setNavigateToTracks(album: Album){
        _navigateToTracks.value = album
    }

    fun navigateToTracksDone(){
        _navigateToTracks.value = null
    }

    init {
        getRankList()
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
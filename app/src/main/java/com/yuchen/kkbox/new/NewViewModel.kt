package com.yuchen.kkbox.new

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yuchen.kkbox.LIMIT
import com.yuchen.kkbox.TERRITORY
import com.yuchen.kkbox.data.*
import com.yuchen.kkbox.data.source.KkboxRepository
import com.yuchen.kkbox.network.LoadApiStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class NewViewModel(private val kkboxRepository: KkboxRepository, private val auth: Auth) : ViewModel() {

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _categoriesResult = MutableLiveData<CategoriesResult>()
    val categoriesResult: LiveData<CategoriesResult>
        get() = _categoriesResult

    private val _newReleaseResult = MutableLiveData<AlbumsResult>()
    val newReleaseResult: LiveData<AlbumsResult>
        get() = _newReleaseResult

    private val _featuredResult = MutableLiveData<AlbumsResult>()
    val featuredResult: LiveData<AlbumsResult>
        get() = _featuredResult

    private val _loadApiStatus = MutableLiveData<LoadApiStatus>()
    val loadApiStatus: LiveData<LoadApiStatus>
        get() = _loadApiStatus

    private val _navigateToTracks = MutableLiveData<Album>()
    val navigateToTracks: LiveData<Album>
        get() = _navigateToTracks

    private val _featuredList = MutableLiveData<List<Album>>()
    val featuredList: LiveData<List<Album>>
        get() = _featuredList

    init {
        getCategoriesList()
        getFeaturedList()
    }

    private fun getCategoriesList() {
        coroutineScope.launch {
            _loadApiStatus.value = LoadApiStatus.LOADING
            val result = kkboxRepository.getCategories(auth,TERRITORY)
            when(result){
                is RepoResult.Success -> {
                    _categoriesResult.value = result.data
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

    fun getNewReleaseAlbumsByCategories(categories:String){
        coroutineScope.launch {
            _loadApiStatus.value = LoadApiStatus.LOADING
            val result = kkboxRepository.getNewReleaseAlbumsByCategories(
                categories, auth, TERRITORY,
                LIMIT
            )
            when(result){
                is RepoResult.Success -> {
                    _newReleaseResult.value = result.data
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

    private fun getFeaturedList(){
        coroutineScope.launch {
            _loadApiStatus.value = LoadApiStatus.LOADING
            val result = kkboxRepository.getFeaturedPlaylists(
                auth, TERRITORY,
                LIMIT
            )
            when(result){
                is RepoResult.Success -> {
                    _featuredResult.value = result.data
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

    fun setFeaturedList(list : List<Album>){
        _featuredList.value = _featuredList.value.orEmpty() + list
    }

    fun setNavigateToTracks(album: Album){
        _navigateToTracks.value = album
    }

    fun navigateToTracksDone(){
        _navigateToTracks.value = null
    }

    fun hasPaging():String?{
        _featuredResult.value?.paging?.next?.let {
            return it
        }
        return null
    }

    fun isLoading():Boolean{
        return _loadApiStatus.value == LoadApiStatus.LOADING
    }

    fun getFeaturedPaging(url: String){
        coroutineScope.launch {
            _loadApiStatus.value = LoadApiStatus.LOADING
            val result = kkboxRepository.getPagingDataByFullUrl(
                url,auth
            )
            when(result){
                is RepoResult.Success -> {
                    _featuredResult.value = result.data
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
package com.yuchen.kkbox.new

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yuchen.kkbox.LIMIT
import com.yuchen.kkbox.TERRITORY
import com.yuchen.kkbox.data.Album
import com.yuchen.kkbox.data.Auth
import com.yuchen.kkbox.data.CategoriesResult
import com.yuchen.kkbox.data.AlbumsResult
import com.yuchen.kkbox.data.source.KkboxRepository
import com.yuchen.kkbox.network.KkboxApi
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
            val categoriesResultDeferred = KkboxApi.kkboxApiService.getCategories(
                "${auth.tokenType} ${auth.accessToken}", TERRITORY
            )
            try {
                _loadApiStatus.value = LoadApiStatus.LOADING
                val categoriesResult = categoriesResultDeferred.await()
                _loadApiStatus.value = LoadApiStatus.DONE
                _categoriesResult.value = categoriesResult
            } catch (t: Throwable) {
                _loadApiStatus.value = LoadApiStatus.ERROR(t.toString())
                _loadApiStatus.value = LoadApiStatus.DONE
            }
        }
    }

    fun getNewReleaseAlbumsByCategories(categories:String){
        coroutineScope.launch {
            val newReleaseAlbumsDeferred = KkboxApi.kkboxApiService.getNewReleaseAlbumsByCategories(
                categories,"${auth.tokenType} ${auth.accessToken}", TERRITORY, LIMIT
            )
            try {
                _loadApiStatus.value = LoadApiStatus.LOADING
                val newReleaseAlbums=newReleaseAlbumsDeferred.await()
                _loadApiStatus.value = LoadApiStatus.DONE
                _newReleaseResult.value = newReleaseAlbums
            }catch (t:Throwable){
                _loadApiStatus.value = LoadApiStatus.ERROR(t.toString())
                _loadApiStatus.value = LoadApiStatus.DONE
            }
        }
    }

    private fun getFeaturedList(){
        coroutineScope.launch {
            val featuredListDeferred = KkboxApi.kkboxApiService.getFeaturedPlaylists(
                "${auth.tokenType} ${auth.accessToken}",
                TERRITORY,
                LIMIT
            )
            try {
                _loadApiStatus.value = LoadApiStatus.LOADING
                val featuredList=featuredListDeferred.await()
                _loadApiStatus.value = LoadApiStatus.DONE
                _featuredResult.value = featuredList
            }catch (t:Throwable){
                _loadApiStatus.value = LoadApiStatus.ERROR(t.toString())
                _loadApiStatus.value = LoadApiStatus.DONE
            }
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

    fun getFeaturedPaging(url: String){
        coroutineScope.launch {
            val featuredPagingDeferred = KkboxApi.kkboxApiService.getPagingDataByFullUrl(
                url,
                "${auth.tokenType} ${auth.accessToken}"
            )
            try {
                _loadApiStatus.value = LoadApiStatus.LOADING
                val featuredPaging=featuredPagingDeferred.await()
                _loadApiStatus.value = LoadApiStatus.DONE
                _featuredResult.value = featuredPaging
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
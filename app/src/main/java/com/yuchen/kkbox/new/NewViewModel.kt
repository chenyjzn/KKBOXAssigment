package com.yuchen.kkbox.new

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yuchen.kkbox.LIMIT
import com.yuchen.kkbox.R
import com.yuchen.kkbox.TERRITORY
import com.yuchen.kkbox.data.*
import com.yuchen.kkbox.data.source.KkboxRepository
import com.yuchen.kkbox.network.LoadApiStatus
import com.yuchen.kkbox.util.getString
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

    private val _dataItemList = MutableLiveData<List<DataItem>>()
    val dataItemList: LiveData<List<DataItem>>
        get() = _dataItemList

    var nextPagingUrl:Paging? = null

    private var isFeaturedFromPaging = false
    private var isFeaturedInit = false
    private var isNewReleaseInit = false

    init {
        getCategoriesList()
        getFeaturedList()
    }

    fun setCategoriesResultDone(){
        _categoriesResult.value = null
    }
    fun setNewReleaseResultDone(){
        _newReleaseResult.value = null
    }
    fun setFeaturedResultDone(){
        _featuredResult.value = null
    }

    fun getIsFeaturedFromPaging():Boolean{
        return isFeaturedFromPaging
    }

    fun getIsFeaturedInit():Boolean{
        return isFeaturedInit
    }

    fun getIsNewReleaseInit():Boolean{
        return isNewReleaseInit
    }

    fun setNewReleaseResultErr(){
        _newReleaseResult.value = AlbumsResult(error = getString(R.string.categories_result_fail))
    }

    fun isFeaturedResultNull():Boolean{
        return _featuredResult.value == null
    }

    fun isNewReleaseResultNull():Boolean{
        return _newReleaseResult.value == null
    }

    fun initDataItemList(){
        _newReleaseResult.value?.let {newRelease ->
            _featuredResult.value?.let {featured ->
                if (newRelease.data.isNotEmpty()){
                    val newReleaseTitle = DataItem.Title(getString(R.string.new_rls_title))
                    val newReleaseList = DataItem.NewReleaseList(newRelease.data)
                    _dataItemList.value = listOf(newReleaseTitle,newReleaseList) + _dataItemList.value.orEmpty()
                    isNewReleaseInit = true
                }
                if (featured.data.isNotEmpty()){
                    val featuredTitle = DataItem.Title(getString(R.string.new_featured_title))
                    val featuredList = featured.data.map {
                        DataItem.Featured(it)
                    }
                    _dataItemList.value = _dataItemList.value.orEmpty() + featuredTitle + featuredList
                    isFeaturedInit = true
                }
            }
        }
    }

    fun setNewRelease(list: List<Album>){
        if (list.isNotEmpty()&&!isNewReleaseInit){
            val newReleaseTitle = DataItem.Title(getString(R.string.new_rls_title))
            val newReleaseList = DataItem.NewReleaseList(list)
            _dataItemList.value = listOf(newReleaseTitle,newReleaseList) + _dataItemList.value.orEmpty()
            isNewReleaseInit = true
        }
    }

    fun setFeatured(list: List<Album>){
        if (list.isNotEmpty()){
            if (isFeaturedFromPaging&&isFeaturedInit) {
                val featuredList = list.map {
                    DataItem.Featured(it)
                }
                _dataItemList.value = _dataItemList.value.orEmpty() + featuredList
            }else if(!isFeaturedInit){
                val featuredTitle = DataItem.Title(getString(R.string.new_featured_title))
                val featuredList = list.map {
                    DataItem.Featured(it)
                }
                _dataItemList.value = _dataItemList.value.orEmpty() + featuredTitle + featuredList
                isFeaturedInit = true
            }
        }
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
                    isFeaturedFromPaging = false
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

    fun setNavigateToTracks(album: Album){
        _navigateToTracks.value = album
    }

    fun navigateToTracksDone(){
        _navigateToTracks.value = null
    }

    fun hasPaging():String?{
        nextPagingUrl?.let {
            return it.next
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
                    isFeaturedFromPaging = true
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
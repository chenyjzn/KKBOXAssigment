package com.yuchen.kkbox.data.source.remote

import com.yuchen.kkbox.R
import com.yuchen.kkbox.data.*
import com.yuchen.kkbox.data.source.KkboxRemoteInterface
import com.yuchen.kkbox.network.KkboxApi
import com.yuchen.kkbox.network.KkboxAuthApi
import com.yuchen.kkbox.util.Util.isInternetConnected
import com.yuchen.kkbox.util.getString
import java.lang.Exception

object KkboxRemoteDataSource : KkboxRemoteInterface {
    override suspend fun getKKBOXAuth(grantType : String,clientId : String,clientSecret : String): RepoResult<Auth> {
        if (!isInternetConnected())
            return RepoResult.Err(getString(R.string.internet_not_connected))
        val authResultDeferred = KkboxAuthApi.kkboxAuthApiService.getKKBOXAuth(grantType,clientId,clientSecret)
        return try {
            val authResult=authResultDeferred.await()
            if (authResult.error!=null){
                return RepoResult.Err(authResult.error)
            }
            RepoResult.Success(authResult)
        }catch (e:Exception){
            RepoResult.Except(e)
        }
    }

    override suspend fun getRankList(auth: Auth,territory: String): RepoResult<AlbumsResult> {
        val rankResultDeferred = KkboxApi.kkboxApiService.getRankList(
            "${auth.tokenType} ${auth.accessToken}", territory
        )
        return try {
            val rankResult=rankResultDeferred.await()
            if (rankResult.error!=null){
                return RepoResult.Err(rankResult.error)
            }
            RepoResult.Success(rankResult)
        }catch (e:Exception){
            RepoResult.Except(e)
        }
    }

    override suspend fun getPagingDataByFullUrl(url: String,auth: Auth): RepoResult<AlbumsResult> {
        val featuredPagingDeferred = KkboxApi.kkboxApiService.getPagingDataByFullUrl(
            url,
            "${auth.tokenType} ${auth.accessToken}"
        )
        return try {
            val featuredPaging=featuredPagingDeferred.await()
            if (featuredPaging.error!=null){
                return RepoResult.Err(featuredPaging.error)
            }
            RepoResult.Success(featuredPaging)
        }catch (e:Exception){
            RepoResult.Except(e)
        }
    }

    override suspend fun getFeaturedPlaylists(auth: Auth,territory:String,limit: Int): RepoResult<AlbumsResult> {
        val featuredListDeferred = KkboxApi.kkboxApiService.getFeaturedPlaylists(
            "${auth.tokenType} ${auth.accessToken}",
            territory,
            limit
        )
        return try {
            val featuredList=featuredListDeferred.await()
            if (featuredList.error!=null){
                return RepoResult.Err(featuredList.error)
            }
            RepoResult.Success(featuredList)
        }catch (e:Exception){
            RepoResult.Except(e)
        }
    }

    override suspend fun getCategories(auth:Auth,territory:String): RepoResult<CategoriesResult> {
        val categoriesResultDeferred = KkboxApi.kkboxApiService.getCategories(
            "${auth.tokenType} ${auth.accessToken}", territory
        )
        return try {
            val categoriesResult = categoriesResultDeferred.await()
            if (categoriesResult.error!=null){
                return RepoResult.Err(categoriesResult.error)
            }
            RepoResult.Success(categoriesResult)
        }catch (e:Exception){
            RepoResult.Except(e)
        }
    }

    override suspend fun getNewReleaseAlbumsByCategories(categories:String,auth:Auth, territory:String,limit:Int): RepoResult<AlbumsResult> {
        val newReleaseAlbumsDeferred = KkboxApi.kkboxApiService.getNewReleaseAlbumsByCategories(
            categories,"${auth.tokenType} ${auth.accessToken}", territory, limit
        )
        return try {
            val newReleaseAlbums=newReleaseAlbumsDeferred.await()
            if (newReleaseAlbums.error!=null){
                return RepoResult.Err(newReleaseAlbums.error)
            }
            RepoResult.Success(newReleaseAlbums)
        }catch (e:Exception){
            RepoResult.Except(e)
        }
    }

    override suspend fun getTracksByAlbum(album:Album,auth: Auth,territory: String,offset:Int,limit: Int): RepoResult<TracksResult> {
        val tracksResultDeferred =
            KkboxApi.kkboxApiService.getTracksByAlbum(
                album.id,
                "${auth.tokenType} ${auth.accessToken}",
                territory,
                offset,
                limit
            )
        return try {
            val tracksResult=tracksResultDeferred.await()
            if (tracksResult.error!=null){
                return RepoResult.Err(tracksResult.error)
            }
            RepoResult.Success(tracksResult)
        }catch (e:Exception){
            RepoResult.Except(e)
        }
    }

    override suspend fun getTracksByChart(album:Album,auth: Auth,territory: String,offset:Int,limit: Int): RepoResult<TracksResult> {
        val tracksResultDeferred =
            KkboxApi.kkboxApiService.getTracksByChart(
                album.id,
                "${auth.tokenType} ${auth.accessToken}",
                territory,
                offset,
                limit
            )
        return try {
            val tracksResult = tracksResultDeferred.await()
            if (tracksResult.error != null) {
                return RepoResult.Err(tracksResult.error)
            }
            RepoResult.Success(tracksResult)
        } catch (e: Exception) {
            RepoResult.Except(e)
        }
    }
}
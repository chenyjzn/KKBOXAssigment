package com.yuchen.kkbox.data.source

import com.yuchen.kkbox.data.*

interface KkboxRemoteInterface {

    suspend fun getKKBOXAuth(grantType : String,clientId : String,clientSecret : String): RepoResult<Auth>

    suspend fun getRankList(auth: Auth,territory: String): RepoResult<AlbumsResult>

    suspend fun getPagingDataByFullUrl(url: String,auth: Auth): RepoResult<AlbumsResult>

    suspend fun getFeaturedPlaylists(auth: Auth,territory:String,limit: Int): RepoResult<AlbumsResult>

    suspend fun getCategories(auth:Auth,territory:String): RepoResult<CategoriesResult>

    suspend fun getNewReleaseAlbumsByCategories(categories:String,auth:Auth, territory:String,limit:Int): RepoResult<AlbumsResult>

    suspend fun getTracksByAlbum(album:Album,auth: Auth,territory: String,offset:Int,limit: Int): RepoResult<TracksResult>

    suspend fun getTracksByChart(album:Album,auth: Auth,territory: String,offset:Int,limit: Int): RepoResult<TracksResult>

}
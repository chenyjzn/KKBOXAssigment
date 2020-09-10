package com.yuchen.kkbox.data.source

import com.yuchen.kkbox.data.*

class DefultKkboxRepository (val kkboxRemoteinterface : KkboxRemoteInterface) : KkboxRepository{

    override suspend fun getKKBOXAuth(grantType : String,clientId : String,clientSecret : String): RepoResult<Auth> {
        return kkboxRemoteinterface.getKKBOXAuth(grantType,clientId,clientSecret)
    }

    override suspend fun getRankList(auth: Auth,territory: String): RepoResult<AlbumsResult> {
        return kkboxRemoteinterface.getRankList(auth,territory)
    }

    override suspend fun getPagingDataByFullUrl(url: String,auth: Auth): RepoResult<AlbumsResult> {
        return kkboxRemoteinterface.getPagingDataByFullUrl(url,auth)
    }

    override suspend fun getFeaturedPlaylists(auth: Auth,territory:String,limit: Int): RepoResult<AlbumsResult> {
        return kkboxRemoteinterface.getFeaturedPlaylists(auth,territory,limit)
    }

    override suspend fun getCategories(auth:Auth,territory:String): RepoResult<CategoriesResult> {
        return kkboxRemoteinterface.getCategories(auth,territory)
    }

    override suspend fun getNewReleaseAlbumsByCategories(categories:String,auth:Auth, territory:String,limit:Int): RepoResult<AlbumsResult> {
        return kkboxRemoteinterface.getNewReleaseAlbumsByCategories(categories,auth, territory,limit)
    }

    override suspend fun getTracksByAlbum(album:Album,auth: Auth,territory: String,offset:Int,limit: Int): RepoResult<TracksResult> {
        return kkboxRemoteinterface.getTracksByAlbum(album,auth,territory,offset,limit)
    }

    override suspend fun getTracksByChart(album:Album,auth: Auth,territory: String,offset:Int,limit: Int): RepoResult<TracksResult> {
        return kkboxRemoteinterface.getTracksByChart(album,auth,territory,offset,limit)
    }

}
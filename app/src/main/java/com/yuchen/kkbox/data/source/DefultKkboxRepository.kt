package com.yuchen.kkbox.data.source

import com.yuchen.kkbox.data.*

class DefultKkboxRepository (val kkboxRemoteinterface : KkboxRemoteInterface) : KkboxRepository{

    override suspend fun getKKBOXAuth(): RepoResult<Auth> {
        return kkboxRemoteinterface.getKKBOXAuth()
    }

    override suspend fun getRankList(): RepoResult<AlbumsResult> {
        return kkboxRemoteinterface.getRankList()
    }

    override suspend fun getPagingDataByFullUrl(): RepoResult<AlbumsResult> {
        return kkboxRemoteinterface.getPagingDataByFullUrl()
    }

    override suspend fun getFeaturedPlaylists(): RepoResult<AlbumsResult> {
        return kkboxRemoteinterface.getFeaturedPlaylists()
    }

    override suspend fun getCategories(): RepoResult<CategoriesResult> {
        return kkboxRemoteinterface.getCategories()
    }

    override suspend fun getNewReleaseAlbumsByCategories(): RepoResult<AlbumsResult> {
        return kkboxRemoteinterface.getNewReleaseAlbumsByCategories()
    }

    override suspend fun getTracksByAlbum(): RepoResult<TracksResult> {
        return kkboxRemoteinterface.getTracksByAlbum()
    }

    override suspend fun getTracksByChart(): RepoResult<TracksResult> {
        return kkboxRemoteinterface.getTracksByChart()
    }

}
package com.yuchen.kkbox.data.source

import com.yuchen.kkbox.data.*

interface KkboxRemoteInterface {

    suspend fun getKKBOXAuth(): RepoResult<Auth>

    suspend fun getRankList(): RepoResult<AlbumsResult>

    suspend fun getPagingDataByFullUrl(): RepoResult<AlbumsResult>

    suspend fun getFeaturedPlaylists(): RepoResult<AlbumsResult>

    suspend fun getCategories(): RepoResult<CategoriesResult>

    suspend fun getNewReleaseAlbumsByCategories(): RepoResult<AlbumsResult>

    suspend fun getTracksByAlbum(): RepoResult<TracksResult>

    suspend fun getTracksByChart(): RepoResult<TracksResult>

}
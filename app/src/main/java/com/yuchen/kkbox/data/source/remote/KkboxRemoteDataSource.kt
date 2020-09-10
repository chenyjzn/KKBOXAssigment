package com.yuchen.kkbox.data.source.remote

import com.yuchen.kkbox.data.*
import com.yuchen.kkbox.data.source.KkboxRemoteInterface

object KkboxRemoteDataSource : KkboxRemoteInterface {
    override suspend fun getKKBOXAuth(): RepoResult<Auth> {
        TODO("Not yet implemented")
    }

    override suspend fun getRankList(): RepoResult<AlbumsResult> {
        TODO("Not yet implemented")
    }

    override suspend fun getPagingDataByFullUrl(): RepoResult<AlbumsResult> {
        TODO("Not yet implemented")
    }

    override suspend fun getFeaturedPlaylists(): RepoResult<AlbumsResult> {
        TODO("Not yet implemented")
    }

    override suspend fun getCategories(): RepoResult<CategoriesResult> {
        TODO("Not yet implemented")
    }

    override suspend fun getNewReleaseAlbumsByCategories(): RepoResult<AlbumsResult> {
        TODO("Not yet implemented")
    }

    override suspend fun getTracksByAlbum(): RepoResult<TracksResult> {
        TODO("Not yet implemented")
    }

    override suspend fun getTracksByChart(): RepoResult<TracksResult> {
        TODO("Not yet implemented")
    }
}
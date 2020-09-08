package com.yuchen.kkbox.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.yuchen.kkbox.data.AlbumsResult
import com.yuchen.kkbox.data.Auth
import com.yuchen.kkbox.data.CategoriesResult
import com.yuchen.kkbox.data.TracksResult
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*


private const val KKBOX_AUTH_API = "https://account.kkbox.com/"
private const val KKBOX_API = "https://api.kkbox.com/"

private val moshi= Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

private val client = OkHttpClient.Builder()
    .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC })
    .build()

private val retrofitKkboxAuth = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .client(client)
    .baseUrl(KKBOX_AUTH_API)
    .build()

private val retrofitKkbox = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .client(client)
    .baseUrl(KKBOX_API)
    .build()

interface KkboxAuthApiService {
    @FormUrlEncoded
    @POST("oauth2/token")
    fun getKKBOXAuth(
        @Field("grant_type") grantType: String,
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String
    ): Deferred<Auth>
}

object KkboxAuthApi{
    val kkboxAuthApiService:KkboxAuthApiService by lazy {
        retrofitKkboxAuth.create(KkboxAuthApiService::class.java)
    }
}

interface KkboxApiService {
    @GET("v1.1/charts")
    fun getRankList(
        @Header("Authorization") Authorization: String,
        @Query("territory") territory: String
    ): Deferred<AlbumsResult>

    @GET
    fun getPagingDataByFullUrl(
        @Url url: String?,
        @Header("Authorization") Authorization: String
    ): Deferred<AlbumsResult>

    @GET("v1.1/featured-playlists")
    fun getFeaturedPlaylists(
        @Header("Authorization") Authorization: String,
        @Query("territory") territory: String,
        @Query("limit") limit: Int
    ): Deferred<AlbumsResult>

    @GET("v1.1/new-release-categories")
    fun getCategories(
        @Header("Authorization") Authorization: String,
        @Query("territory") territory: String
    ): Deferred<CategoriesResult>

    @GET("v1.1/new-release-categories/{categories}/albums")
    fun getNewReleaseAlbumsByCategories(
        @Path("categories") categories:String,
        @Header("Authorization") Authorization: String,
        @Query("territory") territory: String,
        @Query("limit") limit: Int
    ): Deferred<AlbumsResult>

    @GET("v1.1/albums/{albumId}/tracks")
    fun getTracksByAlbum(
        @Path("albumId") albumId:String,
        @Header("Authorization") Authorization: String,
        @Query("territory") territory: String,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Deferred<TracksResult>

    @GET("v1.1/charts/{albumId}/tracks")
    fun getTracksByChart(
        @Path("albumId") albumId:String,
        @Header("Authorization") Authorization: String,
        @Query("territory") territory: String,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Deferred<TracksResult>
}

object KkboxApi{
    val kkboxApiService:KkboxApiService by lazy {
        retrofitKkbox.create(KkboxApiService::class.java)
    }
}
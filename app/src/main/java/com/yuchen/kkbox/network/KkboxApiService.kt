package com.yuchen.kkbox.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.yuchen.kkbox.data.Auth
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

private const val KKBOX_API = "https://account.kkbox.com/"

private val moshi= Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

private val client = OkHttpClient.Builder()
    .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC })
    .build()

private val retrofitKkbox = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .client(client)
    .baseUrl(KKBOX_API)
    .build()

interface KkboxApiService{
    @FormUrlEncoded
    @POST("oauth2/token")
    fun getKKBOXAuth(@Field("grant_type") grantType:String,@Field("client_id") clientId:String,@Field("client_secret") clientSecret:String): Deferred<Auth>
}

object KkboxApi{
    val kkboxApiService:KkboxApiService by lazy {
        retrofitKkbox.create(KkboxApiService::class.java)
    }
}
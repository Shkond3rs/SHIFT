package com.example.shift

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitClient {
    // Базовый URL для API RandomUser
    private const val BASE_URL = "https://randomuser.me"
    // Interceptor для логирования запросов и ответов в LogCat
    private val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    // Клиент для выполнения запросов
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(logger)
        .build()

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    // Retrofit клиент для выполнения запросов к API
    val randomUserAPIService: RandomUserAPIService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(RandomUserAPIService::class.java)
    }
}
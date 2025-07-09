package com.example.shift

import retrofit2.http.GET
import retrofit2.http.Query

interface RandomUserAPIService {
    @GET("/api")
    suspend fun getRandomUsers(
        @Query("results") results: Int,
        @Query("noinfo") noinfo: Boolean = true
    ): RandomUserResponse
}
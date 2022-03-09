package com.example.paginationscratchapp.data.api

import com.example.paginationscratchapp.data.CommunityResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface CommunityService {
    @GET("api/community_{page}.json")
    suspend fun getCommunity(@Path("page")page:Int): CommunityResponse
}
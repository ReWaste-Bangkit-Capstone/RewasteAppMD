package com.example.rewasteappmd.network

import com.example.rewasteappmd.network.response.BaseResponse
import com.example.rewasteappmd.network.response.HandicraftDetailResponse
import com.example.rewasteappmd.network.response.HandicraftResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface NetworkService {

    @GET("/api/v1/handicrafts")
    suspend fun getHandicrafts(): Response<BaseResponse<List<HandicraftResponse>>>

    @GET("/api/v1/handicrafts/{id}")
    suspend fun getHandicraftDetail(
        @Path("id") id: String
    ): Response<BaseResponse<HandicraftDetailResponse>>




}
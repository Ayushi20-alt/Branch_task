package com.example.branchtask.data.remote

import com.example.branchtask.domain.models.HomemodelItem
import com.example.branchtask.domain.models.MessageRequest
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface HomeAPi {

    @GET("/api/messages")
    suspend fun getinfo() : Response<ArrayList<HomemodelItem>>

    @POST("api/messages")
    suspend fun createMessage(@Body messageRequest: MessageRequest): Response<HomemodelItem>

    @POST("api/reset")
    suspend fun reset() : Response<ResponseBody>
}
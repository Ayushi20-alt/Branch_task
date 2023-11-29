package com.example.branchtask.data.remote

import com.example.branchtask.domain.models.Homemodel
import com.example.branchtask.domain.models.LoginResponse
import com.example.branchtask.domain.models.Loginmodel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface UserApi {

    @POST("api/login")
    suspend fun login(
        @Body loginmodel: Loginmodel
    ): Response<LoginResponse>

}
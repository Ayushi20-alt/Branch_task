package com.example.branchtask.data.remote

import com.example.branchtask.common.TokenManager
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class OathInterceptor  @Inject constructor() : Interceptor {

    @Inject
    lateinit var tokenManager: TokenManager

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()

        val token = tokenManager.getToken()
        request.addHeader("X-Branch-Auth-Token", "$token")
        return chain.proceed(request.build())
    }
}
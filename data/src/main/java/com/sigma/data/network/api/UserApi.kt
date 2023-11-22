package com.sigma.data.network.api

import com.sigma.data.network.dto.user.NewAccessTokenResponse
import com.sigma.data.network.dto.user.UserResponse
import retrofit2.http.GET
import retrofit2.http.Header

interface UserApi {

    @GET("/api/member/member")
    suspend fun getUser(): UserResponse

    @GET("/api/member/newAccessToken")
    suspend fun getAccessToken(
        @Header("refreshToken") refreshToken: String
    ): NewAccessTokenResponse

}
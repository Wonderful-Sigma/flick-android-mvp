package com.sigma.data.network.api

import com.sigma.data.network.dto.user.NewAccessTokenResponseDto
import com.sigma.data.network.dto.user.UserResponseDto
import retrofit2.http.GET
import retrofit2.http.Header

interface UserApi {

    @GET("/api/member/member")
    suspend fun getUser(): UserResponseDto

    @GET("/api/member/newAccessToken")
    suspend fun getAccessToken(
        @Header("refreshToken") refreshToken: String
    ): NewAccessTokenResponseDto

}
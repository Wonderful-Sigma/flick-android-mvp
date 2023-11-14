package com.sigma.data.network.api

import com.sigma.data.network.dto.dauth.DauthRequestDto
import com.sigma.data.network.dto.dauth.DauthResponseDto
import com.sigma.data.network.dto.user.NewAccessTokenResponseDto
import com.sigma.data.network.dto.user.UserResponseDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface MemberApi {

    @POST("/api/member/login")
    suspend fun login(
        @Body dauthRequest: DauthRequestDto
    ): DauthResponseDto

    @GET("/api/member/member")
    suspend fun getUser(): UserResponseDto

    @GET("/api/member/newAccessToken")
    suspend fun getAccessToken(
        @Header("RefreshToken") refreshToken: String
    ): NewAccessTokenResponseDto

}
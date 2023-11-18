package com.sigma.data.network.api

import com.sigma.data.network.dto.dauth.DauthRequest
import com.sigma.data.network.dto.dauth.DauthResponse
import com.sigma.data.network.dto.user.NewAccessTokenResponse
import com.sigma.data.network.dto.user.UserResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface MemberApi {

    @POST("/api/member/login")
    suspend fun login(
        @Body dauthRequest: DauthRequest
    ): DauthResponse

    @GET("/api/member/member")
    suspend fun getUser(): UserResponse

    @GET("/api/member/newAccessToken")
    suspend fun getAccessToken(
        @Header("RefreshToken") refreshToken: String
    ): NewAccessTokenResponse

}
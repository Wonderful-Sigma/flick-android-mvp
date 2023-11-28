package com.sigma.data.network.api

import com.sigma.data.network.FlickUrl
import com.sigma.data.network.dto.user.NewAccessTokenResponse
import com.sigma.data.network.dto.user.UserResponse
import retrofit2.http.GET
import retrofit2.http.Header

interface UserApi {

    @GET(FlickUrl.Member.member)
    suspend fun getUser(): UserResponse

    @GET(FlickUrl.Member.newAccessToken)
    suspend fun getAccessToken(
        @Header("refreshToken") refreshToken: String
    ): NewAccessTokenResponse

}
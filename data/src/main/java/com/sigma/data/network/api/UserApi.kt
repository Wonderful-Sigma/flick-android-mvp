package com.sigma.data.network.api

import com.sigma.data.network.FlickUrl
import com.sigma.data.network.dto.dauth.DauthRequest
import com.sigma.data.network.dto.dauth.DauthResponse
import com.sigma.data.network.dto.user.NewAccessTokenResponse
import com.sigma.data.network.dto.user.UserResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface UserApi {

    @POST(FlickUrl.Member.login)
    suspend fun login(
        @Body dauthRequest: DauthRequest
    ): DauthResponse

    @GET(FlickUrl.Member.member)
    suspend fun getUser(): UserResponse

    @GET(FlickUrl.Member.newAccessToken)
    suspend fun getAccessToken(
        @Header("refreshToken") refreshToken: String
    ): NewAccessTokenResponse

}
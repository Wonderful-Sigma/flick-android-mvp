package com.sigma.data.network.api

import com.sigma.data.network.dto.user.UserResponseDto
import retrofit2.http.GET
import retrofit2.http.Header

interface UserApi {

    @GET("/api/member")
    suspend fun getUser(): UserResponseDto

    @GET("/api/newAccessToken") // todo ToKen? , K가 왜 대문자야?
    suspend fun getAccessToken(
        @Header("refreshToken") refreshToken: String
    )

}
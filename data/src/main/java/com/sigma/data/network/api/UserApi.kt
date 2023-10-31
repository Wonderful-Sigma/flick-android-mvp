package com.sigma.data.network.api

import com.sigma.data.network.dto.user.UserResponseDto
import retrofit2.http.GET

interface UserApi {

    @GET("/api/member")
    suspend fun getUser(): UserResponseDto

}
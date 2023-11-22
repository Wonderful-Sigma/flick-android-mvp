package com.sigma.data.network.api

import com.sigma.data.network.dto.dauth.DauthRequestDto
import com.sigma.data.network.dto.dauth.DauthResponseDto
import retrofit2.http.Body
import retrofit2.http.POST

interface DauthApi {

    @POST("/api/member/login")
    suspend fun login(
        @Body dauthRequest: DauthRequestDto
    ): DauthResponseDto

}
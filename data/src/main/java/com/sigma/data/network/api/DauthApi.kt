package com.sigma.data.network.api

import com.sigma.data.network.dto.dauth.DauthRequest
import com.sigma.data.network.dto.dauth.DauthResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface DauthApi {

    @POST("/api/member/login")
    suspend fun login(
        @Body dauthRequest: DauthRequest
    ): DauthResponse

}
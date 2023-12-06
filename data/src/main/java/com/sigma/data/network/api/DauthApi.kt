package com.sigma.data.network.api

import com.sigma.data.network.dto.Response
import com.sigma.data.network.dto.dauth.DauthLoginRequest
import com.sigma.data.network.dto.dauth.DauthLoginResponse
import retrofit2.http.POST

interface DauthApi {

    @POST("/auth/login")
    suspend fun dauthLogin(
        dauthLoginRequest: DauthLoginRequest
    ): Response<DauthLoginResponse>

}
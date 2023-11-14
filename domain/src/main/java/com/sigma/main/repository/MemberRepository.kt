package com.sigma.main.repository

import com.sigma.main.model.dauth.DauthRequestModel
import com.sigma.main.model.dauth.DauthResponseModel
import com.sigma.main.model.user.NewAccessTokenResponseModel
import com.sigma.main.model.user.UserResponseModel

interface MemberRepository {

    suspend fun login(
        dauthRequestDto: DauthRequestModel
    ): DauthResponseModel

    suspend fun getUser(): UserResponseModel

    suspend fun getAccessToken(
        refreshToken: String
    ): NewAccessTokenResponseModel

}
package com.sigma.data.repository

import com.sigma.data.network.api.MemberApi
import com.sigma.data.network.dto.dauth.DauthRequest
import com.sigma.data.network.dto.dauth.DauthResponse
import com.sigma.data.network.dto.user.NewAccessTokenResponse
import com.sigma.data.network.dto.user.UserResponse
import javax.inject.Inject

class MemberRepository @Inject constructor(
    private val memberApi: MemberApi
) {
    suspend fun login(dauthRequestDto: DauthRequest): DauthResponse =
        memberApi.login(dauthRequestDto)

    suspend fun getUser(): UserResponse =
        memberApi.getUser()

    suspend fun getAccessToken(refreshToken: String): NewAccessTokenResponse =
        memberApi.getAccessToken(refreshToken)

}
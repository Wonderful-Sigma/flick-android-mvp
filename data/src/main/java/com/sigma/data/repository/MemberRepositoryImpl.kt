package com.sigma.data.repository

import com.sigma.data.mapper.toDto
import com.sigma.data.mapper.toModel
import com.sigma.data.network.api.MemberApi
import com.sigma.main.model.dauth.DauthRequestModel
import com.sigma.main.model.dauth.DauthResponseModel
import com.sigma.main.model.user.NewAccessTokenResponseModel
import com.sigma.main.model.user.UserResponseModel
import com.sigma.main.repository.MemberRepository
import javax.inject.Inject

class MemberRepositoryImpl @Inject constructor(
    private val memberApi: MemberApi
): MemberRepository {
    override suspend fun login(dauthRequestDto: DauthRequestModel): DauthResponseModel =
        memberApi.login(dauthRequestDto.toDto()).toModel()

    override suspend fun getUser(): UserResponseModel =
        memberApi.getUser().toModel()

    override suspend fun getAccessToken(refreshToken: String): NewAccessTokenResponseModel =
        memberApi.getAccessToken(refreshToken).toModel()

}
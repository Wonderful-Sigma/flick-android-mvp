package com.sigma.data.repository

import com.sigma.data.mapper.toModel
import com.sigma.data.network.api.UserApi
import com.sigma.main.model.user.NewAccessTokenResponseModel
import com.sigma.main.model.user.UserResponseModel
import com.sigma.main.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userApi: UserApi
): UserRepository {

    override suspend fun getUser(): UserResponseModel =
        userApi.getUser().toModel()

    override suspend fun getAccessToken(refreshToken: String): NewAccessTokenResponseModel =
        userApi.getAccessToken(refreshToken).toModel()

}
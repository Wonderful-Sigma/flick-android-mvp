package com.sigma.main.repository

import com.sigma.main.model.user.UserResponseModel

interface UserRepository {

    suspend fun getUser(): UserResponseModel

    suspend fun getAccessToken(
        refreshToken: String
    )

}
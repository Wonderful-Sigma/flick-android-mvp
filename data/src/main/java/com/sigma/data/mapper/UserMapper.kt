package com.sigma.data.mapper

import com.sigma.data.network.dto.user.NewAccessTokenResponseDto
import com.sigma.data.network.dto.user.UserResponseDto
import com.sigma.main.model.user.NewAccessTokenResponseModel
import com.sigma.main.model.user.UserResponseModel

fun UserResponseDto.toModel() = UserResponseModel(
    id = this.id,
    studentNumber = this.studentNumber,
    name = this.name,
    firebaseToken = this.firebaseToken,
    memberRule = this.memberRule,
    account = this.account,
)

fun NewAccessTokenResponseDto.toModel() = NewAccessTokenResponseModel(
    newAccess = this.newAccess
)
package com.sigma.data.mapper

import com.sigma.data.network.dto.dauth.DauthRequestDto
import com.sigma.data.network.dto.dauth.DauthResponseDto
import com.sigma.data.network.dto.user.NewAccessTokenResponseDto
import com.sigma.data.network.dto.user.UserResponseDto
import com.sigma.main.model.dauth.DauthRequestModel
import com.sigma.main.model.dauth.DauthResponseModel
import com.sigma.main.model.user.NewAccessTokenResponseModel
import com.sigma.main.model.user.UserResponseModel

fun DauthRequestModel.toDto() : DauthRequestDto = DauthRequestDto(
    code = this.code
)

fun DauthResponseDto.toModel() : DauthResponseModel = DauthResponseModel(
    accessToken = this.accessToken,
    refreshToken = this.refreshToken
)

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
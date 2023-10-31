package com.sigma.data.mapper

import com.sigma.data.network.dto.dauth.DauthRequestDto
import com.sigma.data.network.dto.dauth.DauthResponseDto
import com.sigma.main.model.dauth.DauthResponseModel
import com.sigma.main.model.dauth.DauthRequestModel

fun DauthRequestModel.toDto() : DauthRequestDto = DauthRequestDto(
    code = this.code
)

fun DauthResponseDto.toModel() : DauthResponseModel = DauthResponseModel(
    accessToken = this.accessToken,
    refreshToken = this.refreshToken
)
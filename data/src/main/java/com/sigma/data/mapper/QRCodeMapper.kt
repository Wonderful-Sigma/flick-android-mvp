package com.sigma.data.mapper

import com.sigma.data.network.dto.qrcode.JwtGenerateResponseDto
import com.sigma.data.network.dto.qrcode.JwtDecodingResponseDto
import com.sigma.main.model.qrcode.JwtGenerateResponseModel
import com.sigma.main.model.qrcode.JwtDecodingResponseModel

fun JwtGenerateResponseDto.toModel() = JwtGenerateResponseModel(
    jwt = this.jwt
)

fun JwtDecodingResponseDto.toModel() = JwtDecodingResponseModel(
    id = this.id,
    name = this.name,
    number = this.number
)
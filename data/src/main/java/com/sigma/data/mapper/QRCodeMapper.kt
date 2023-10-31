package com.sigma.data.mapper

import com.sigma.data.network.dto.qrcode.JWTGenerateResponseDto
import com.sigma.data.network.dto.qrcode.JwtDecodingResponseDto
import com.sigma.main.model.qrcode.JWTGenerateResponseModel
import com.sigma.main.model.qrcode.JwtDecodingResponseModel

fun JWTGenerateResponseDto.toModel() = JWTGenerateResponseModel(
    jwt = this.jwt
)

fun JwtDecodingResponseDto.toModel() = JwtDecodingResponseModel(
    id = this.id,
    name = this.name,
    number = this.number
)
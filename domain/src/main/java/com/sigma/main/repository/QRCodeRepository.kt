package com.sigma.main.repository

import com.sigma.main.model.qrcode.JWTGenerateResponseModel
import com.sigma.main.model.qrcode.JwtDecodingResponseModel

interface QRCodeRepository {

    suspend fun generateJwt(
        walletId: Long
    ): JWTGenerateResponseModel

    suspend fun decodingJwt(
        jwt: String
    ): JwtDecodingResponseModel

}
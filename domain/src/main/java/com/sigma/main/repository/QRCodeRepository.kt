package com.sigma.main.repository

import com.sigma.main.model.qrcode.JwtDecodingResponseModel
import com.sigma.main.model.qrcode.JwtGenerateResponseModel

interface QRCodeRepository {

    suspend fun generateJwt(
        walletId: Long
    ): JwtGenerateResponseModel

    suspend fun decodingJwt(
        jwt: String
    ): JwtDecodingResponseModel

}
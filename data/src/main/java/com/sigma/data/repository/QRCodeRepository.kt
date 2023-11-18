package com.sigma.data.repository

import com.sigma.data.network.api.QRCodeApi
import com.sigma.data.network.dto.qrcode.JwtDecodingResponse
import com.sigma.data.network.dto.qrcode.JwtGenerateResponse
import javax.inject.Inject

class QRCodeRepository @Inject constructor(
    private val qrCodeApi: QRCodeApi
) {

    suspend fun generateJwt(walletId: Long): JwtGenerateResponse =
        qrCodeApi.generateJwt(walletId)

    suspend fun decodingJwt(jwt: String): JwtDecodingResponse =
        qrCodeApi.decodingJwt(jwt)

}
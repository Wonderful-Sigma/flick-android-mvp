package com.sigma.data.repository

import com.sigma.data.mapper.toModel
import com.sigma.data.network.api.QRCodeApi
import com.sigma.main.model.qrcode.JwtDecodingResponseModel
import com.sigma.main.model.qrcode.JwtGenerateResponseModel
import com.sigma.main.repository.QRCodeRepository
import javax.inject.Inject

class QRCodeRepositoryImpl @Inject constructor(
    private val qrCodeApi: QRCodeApi
): QRCodeRepository {

    override suspend fun generateJwt(walletId: Long): JwtGenerateResponseModel =
        qrCodeApi.generateJwt(walletId).toModel()

    override suspend fun decodingJwt(jwt: String): JwtDecodingResponseModel =
        qrCodeApi.decodingJwt(jwt).toModel()

}
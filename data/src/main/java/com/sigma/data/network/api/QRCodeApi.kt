package com.sigma.data.network.api

import com.sigma.data.network.dto.qrcode.JwtDecodingResponseDto
import com.sigma.data.network.dto.qrcode.JwtGenerateResponseDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface QRCodeApi {

    @GET("/api/qrCode/approval/{walletId}")
    suspend fun generateJwt(
        @Path("walletId") walletId: Long
    ): JwtGenerateResponseDto

    @GET("/api/qrCode/search/qr")
    suspend fun decodingJwt(
        @Header("jwt") jwt: String
    ): JwtDecodingResponseDto

}
package com.sigma.data.network.api

import com.sigma.data.network.dto.qrcode.JWTGenerateResponseDto
import com.sigma.data.network.dto.qrcode.JwtDecodingResponseDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface QRCodeApi {

    @GET("/api/approval/{walletId}")
    suspend fun generateJwt(
        @Path("walletId") walletId: Long
    ): JWTGenerateResponseDto

    @GET("/api/search/qr")
    suspend fun decodingJwt(
        @Header("jwt") jwt: String
    ): JwtDecodingResponseDto

}
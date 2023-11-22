package com.sigma.data.network.api

import com.sigma.data.network.dto.qrcode.JwtDecodingResponse
import com.sigma.data.network.dto.qrcode.JwtGenerateResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface QRCodeApi {

    @GET("/api/QrCode/approval/{walletId}")
    suspend fun generateJwt(
        @Path("walletId") walletId: Long
    ): JwtGenerateResponse

    @GET("/api/QrCode/search/qr")
    suspend fun decodingJwt(
        @Header("jwt") jwt: String
    ): JwtDecodingResponse

}
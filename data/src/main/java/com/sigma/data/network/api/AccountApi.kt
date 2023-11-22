package com.sigma.data.network.api

import com.sigma.data.network.dto.account.Account
import com.sigma.data.network.dto.account.AccountResponse
import com.sigma.data.network.dto.account.CheckAlarmResponse
import com.sigma.data.network.dto.account.FixMemberRequest
import com.sigma.data.network.dto.account.EventRequest
import com.sigma.data.network.dto.account.JwtResponse
import com.sigma.data.network.dto.account.MakeZeroRequest
import com.sigma.data.network.dto.account.SpendResponse
import com.sigma.data.network.dto.account.RemitRequest
import com.sigma.data.network.dto.account.MemberResponse
import com.sigma.data.network.dto.account.MemberSetFirebaseRequest
import com.sigma.data.network.dto.account.MessageBodyRequest
import com.sigma.data.network.dto.account.SpendCalculateRequest
import com.sigma.data.network.dto.account.SpendCalculateResponse
import com.sigma.data.network.dto.account.StatusResponse
import com.sigma.data.network.dto.account.UpdatePictureRequest
import com.sigma.data.network.dto.account.WalletResponse
import retrofit2.http.Body
import retrofit2.http.Path
import retrofit2.http.Header
import retrofit2.http.GET
import retrofit2.http.DELETE
import retrofit2.http.PATCH
import retrofit2.http.POST


interface AccountApi {

    //SpendList
    @GET("/api/spend/{walletId}")
    suspend fun allSpend(
        @Path("walletId") walletId: Long
    ): List<List<SpendResponse>>

    @GET("/api/spend/{memberName}/{walletId}")
    suspend fun memberSpend(
        @Path("memberName") memberName: String,
        @Path("walletId") walletId: Long
    ): List<SpendResponse>

    @GET("/api/spend/calculate/oneDay")
    suspend fun spendCalculateDay(
        @Body spendCalculateRequestDto: SpendCalculateRequest
    ): SpendCalculateResponse

    @GET("/api/spend/calculate/oneMonth")
    suspend fun spendCalculateMonth(
        @Body spendCalculateRequestDto: SpendCalculateRequest
    ): SpendCalculateResponse


    //Wallet Management
    @POST("/api/wallet/management/{walletId}")
    suspend fun addMember(
        @Path("walletId") walletId: Long,
        @Body fixMemberRequestDto: FixMemberRequest
    ): StatusResponse

    @DELETE("/api/wallet/management/{walletId}")
    suspend fun deleteMember(
        @Path("walletId") walletId: Long,
        @Body fixMemberRequestDto: FixMemberRequest
    ): StatusResponse

    @PATCH("/api/wallet/management/remit")
    suspend fun remit(
        @Body remitRequestDto: RemitRequest
    ): StatusResponse

    @PATCH("/api/wallet/management/makeZero")
    suspend fun makeZero(
        @Body makeZeroRequestDto: MakeZeroRequest
    ): StatusResponse


    //Manager
    @PATCH("/api/manager/event")
    suspend fun event(
        @Body eventRequestDto: EventRequest
    ): StatusResponse

    //Member
    @GET("/api/member/search/{memberName}")
    suspend fun searchMember(
        @Path("memberName") memberName: String
    ): List<MemberResponse>

    @GET("/api/member/member")
    suspend fun authorizationSearchMember(
        @Header("authorization") authorization: String
    ): MemberResponse

    @POST("/api/member/setFirebase/{memberId}")
    suspend fun setFirebaseToken(
        @Path("memberId") memberId: String,
        @Body memberSetFirebaseRequestDto: MemberSetFirebaseRequest
    )


    //Alarm
    @POST("/api/noti/{walletId}")
    suspend fun requestAlarm(
        @Path("walletId") walletId: String,
        @Body messageBodyRequestDto: MessageBodyRequest
    ): StatusResponse

    @POST("/api/noti/all")
    suspend fun requestAlarmAll(
        @Body messageBodyRequestDto: MessageBodyRequest
    ): StatusResponse

    @GET("/api/noti/{memberId}")
    suspend fun checkAlarm(
        @Path("memberId") memberId: String
    ): CheckAlarmResponse


    //QR Code
    @GET("/api/QrCode/search/qr")
    suspend fun encodingJwt(
        @Header("jwt") jwt: String
    ): AccountResponse

    @GET("/api/QrCode/approval/{walletId}")
    suspend fun createJwt(
        @Path("walletId") walletId: Long
    ): JwtResponse


    //Wallet
    @POST("/api/wallet/{memberId}")
    suspend fun generate(
        @Path("memberId") memberId: String,
        @Body accountName: String
    ): Account

    @GET("/api/wallet/{walletId}")
    suspend fun getWallet(
        @Path("walletId") walletId: Long
    ): WalletResponse

    @GET("/api/wallet/search/accountNumber/{accountNumber}")
    suspend fun getAccount(
        @Path("accountNumber") accountNumber: String
    ): Account

    @GET("/api/wallet/search/{memberId}")
    suspend fun searchWallet(
        @Path("memberId") memberId: String
    ): List<WalletResponse>

    @GET("/api/wallet/search/accountNumber/{accountNumber}")
    suspend fun getWallet(
        @Path("accountNumber") accountNumber: String
    ): WalletResponse

    @GET("/api/wallet/updatePicture/{walletId}")
    suspend fun updatePicture(
        @Path("walletId") walletId: String,
        @Body updatePicture: UpdatePictureRequest
    ): StatusResponse

    @DELETE("/api/wallet/{memberId}/{walletId}")
    suspend fun deleteWallet(
        @Path("memberId") memberId: String,
        @Path("walletId") walletId: Long,
    ): StatusResponse

}
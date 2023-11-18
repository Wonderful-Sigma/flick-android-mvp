package com.sigma.data.network.api

import com.sigma.data.network.dto.account.Account
import com.sigma.data.network.dto.account.AccountObject
import com.sigma.data.network.dto.account.CheckAlarm
import com.sigma.data.network.dto.account.FixMemberRequest
import com.sigma.data.network.dto.account.EventRequest
import com.sigma.data.network.dto.account.MakeZeroRequest
import com.sigma.data.network.dto.account.SpendResponse
import com.sigma.data.network.dto.account.RemitRequest
import retrofit2.http.Body
import retrofit2.http.GET
import com.sigma.data.network.dto.account.MemberResponseDto
import com.sigma.data.network.dto.account.MessageBodyRequest
import com.sigma.data.network.dto.account.SpendCalculateRequest
import com.sigma.data.network.dto.account.StatusResponse
import com.sigma.data.network.dto.account.WalletResponse
import retrofit2.http.DELETE
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface AccountApi {

    //SpendList
    @GET("/api/spend/{walletId}")
    suspend fun allSpend(
        @Path("walletId") walletId: Long
    ): List<List<SpendResponse>>

    @GET("/api/spend/recent/remit/{memberId}")
    suspend fun getRecentAccount(
        @Path("memberId") memberId: String
    ): List<AccountObject>

    @GET("/api/spend/{memberName}/{walletId}")
    suspend fun memberSpend(
        @Path("memberName") memberName: String,
        @Path("walletId") walletId: Long
    ): List<SpendResponse>

    @GET("/api/spend/calculate")
    suspend fun spendCalculate(
        @Body spendCalculateRequestDto: SpendCalculateRequest
    ): StatusResponse






    //Wallet Management
    @POST("/api/{walletId}")
    suspend fun addMember(
        @Path("walletId") walletId: Long,
        @Body fixMemberRequestDto: FixMemberRequest
    ): StatusResponse

    @DELETE("/api/{walletId}")
    suspend fun deleteMember(
        @Path("walletId") walletId: Long,
        @Body fixMemberRequestDto: FixMemberRequest
    ): StatusResponse

    @PATCH("/api/wallet/management/remit")
    suspend fun remit(
        @Body remitRequestDto: RemitRequest
    ): StatusResponse

    @PATCH("/api/makeZero")
    suspend fun makeZero(
        @Body makeZeroRequestDto: MakeZeroRequest
    ): StatusResponse


    //Manager
    @PATCH("/api/manager/event")
    suspend fun event(
        @Body eventRequestDto: EventRequest
    ): StatusResponse

    //Member
    @GET("/api/search/{memberName}")
    suspend fun searchMember(
        @Path("memberName") memberName: String
    ): List<MemberResponseDto>

    @GET("/api/member")
    suspend fun authorizationSearchMember(
        @Header("authorization") authorization: String
    ): MemberResponseDto


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
    ): CheckAlarm



    //Wallet
    @POST("/api/wallet/{memberId}")
    suspend fun generate(
        @Path("memberId") memberId: String,
        @Body accountName: String
    ): AccountObject

    @GET("/api/wallet/search/accountNumber/{accountNumber}")
    suspend fun getAccount(
        @Path("accountNumber") accountNumber: String
    ): AccountObject

    @GET("/api/wallet/{walletId}")
    suspend fun getWallet(
        @Path("walletId") walletId: Long
    ): WalletResponse


    @GET("/api/wallet/search/{memberId}")
    suspend fun searchWallet(
        @Path("memberId") memberId: String
    ): List<WalletResponse>

    @GET("/api/wallet/search/accountNumber/{accountNumber}")
    suspend fun getWallet(
        @Path("accountNumber") accountNumber: String
    ): WalletResponse

    @DELETE("/api/wallet/{memberId}/{walletId}")
    suspend fun deleteWallet(
        @Path("memberId") memberId: String,
        @Path("walletId") walletId: Long,
    ): StatusResponse

}
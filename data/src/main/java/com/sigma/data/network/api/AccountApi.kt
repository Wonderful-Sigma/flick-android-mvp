package com.sigma.data.network.api

import com.sigma.data.network.dto.account.AccountResponseDto
import com.sigma.data.network.dto.account.CheckAlarmDto
import com.sigma.data.network.dto.account.FixMemberRequestDto
import com.sigma.data.network.dto.account.EventRequestDto
import com.sigma.data.network.dto.account.JwtResponseDto
import com.sigma.data.network.dto.account.MakeZeroRequestDto
import com.sigma.data.network.dto.account.SpendResponseDto
import com.sigma.data.network.dto.account.RemitRequestDto
import com.sigma.main.model.account.Account
import retrofit2.http.Body
import retrofit2.http.GET
import com.sigma.data.network.dto.account.MemberResponseDto
import com.sigma.data.network.dto.account.MessageBodyRequestDto
import com.sigma.data.network.dto.account.SpendCalculateRequestDto
import com.sigma.data.network.dto.account.StatusResponseDto
import com.sigma.data.network.dto.account.WalletResponseDto
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
    ): List<List<SpendResponseDto>>

    @GET("/api/spend/recent/remit/{memberId}")
    suspend fun getRecentAccount(
        @Path("memberId") memberId: String
    ): List<Account>

    @GET("/api/spend/{memberName}/{walletId}")
    suspend fun memberSpend(
        @Path("memberName") memberName: String,
        @Path("walletId") walletId: Long
    ): List<SpendResponseDto>

    @GET("/api/spend/calculate")
    suspend fun spendCalculate(
        @Body spendCalculateRequestDto: SpendCalculateRequestDto
    ): StatusResponseDto






    //Wallet Management
    @POST("/api/{walletId}")
    suspend fun addMember(
        @Path("walletId") walletId: Long,
        @Body fixMemberRequestDto: FixMemberRequestDto
    ): StatusResponseDto

    @DELETE("/api/{walletId}")
    suspend fun deleteMember(
        @Path("walletId") walletId: Long,
        @Body fixMemberRequestDto: FixMemberRequestDto
    ): StatusResponseDto

    @PATCH("/api/wallet/management/remit")
    suspend fun remit(
        @Body remitRequestDto: RemitRequestDto
    ): StatusResponseDto

    @PATCH("/api/makeZero")
    suspend fun makeZero(
        @Body makeZeroRequestDto: MakeZeroRequestDto
    ): StatusResponseDto


    //Manager
    @PATCH("/api/manager/event")
    suspend fun event(
        @Body eventRequestDto: EventRequestDto
    ): StatusResponseDto

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
        @Body messageBodyRequestDto: MessageBodyRequestDto
    ): StatusResponseDto

    @POST("/api/noti/all")
    suspend fun requestAlarmAll(
        @Body messageBodyRequestDto: MessageBodyRequestDto
    ): StatusResponseDto

    @GET("/api/noti/{memberId}")
    suspend fun checkAlarm(
        @Path("memberId") memberId: String
    ): CheckAlarmDto



    //Wallet
    @POST("/api/wallet/{memberId}")
    suspend fun generate(
        @Path("memberId") memberId: String,
        @Body accountName: String
    ): Account

    @GET("/api/wallet/search/accountNumber/{accountNumber}")
    suspend fun getAccount(
        @Path("accountNumber") accountNumber: String
    ): Account

    @GET("/api/wallet/{walletId}")
    suspend fun getWallet(
        @Path("walletId") walletId: Long
    ): WalletResponseDto


    @GET("/api/wallet/search/{memberId}")
    suspend fun searchWallet(
        @Path("memberId") memberId: String
    ): List<WalletResponseDto>

    @GET("/api/wallet/search/accountNumber/{accountNumber}")
    suspend fun getWallet(
        @Path("accountNumber") accountNumber: String
    ): WalletResponseDto

    @DELETE("/api/wallet/{memberId}/{walletId}")
    suspend fun deleteWallet(
        @Path("memberId") memberId: String,
        @Path("walletId") walletId: Long,
    ): StatusResponseDto

}
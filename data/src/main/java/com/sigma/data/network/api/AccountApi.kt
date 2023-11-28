package com.sigma.data.network.api

import com.sigma.data.network.FlickUrl
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
    @GET(FlickUrl.Spend.all)
    suspend fun allSpend(
        @Path("walletId") walletId: Long
    ): List<List<SpendResponse>>


    @PATCH(FlickUrl.Wallet.remit)
    suspend fun remit(
        @Body remitRequestDto: RemitRequest
    ): StatusResponse


    //Manager
    @PATCH(FlickUrl.Manager.event)
    suspend fun event(
        @Body eventRequestDto: EventRequest
    ): StatusResponse

    @POST(FlickUrl.Member.firebase)
    suspend fun setFirebaseToken(
        @Path("memberId") memberId: String,
        @Body memberSetFirebaseRequestDto: MemberSetFirebaseRequest
    )

    //Alarm
    @POST(FlickUrl.Notification.alarm)
    suspend fun requestAlarm(
        @Path("walletId") walletId: String,
        @Body messageBodyRequestDto: MessageBodyRequest
    ): StatusResponse


    @GET(FlickUrl.Wallet.get)
    suspend fun getWallet(
        @Path("walletId") walletId: Long
    ): WalletResponse

    @GET(FlickUrl.Wallet.searchToNumber)
    suspend fun getAccount(
        @Path("accountNumber") accountNumber: String
    ): Account

    @GET(FlickUrl.Wallet.searchToId)
    suspend fun searchWallet(
        @Path("memberId") memberId: String
    ): List<WalletResponse>

}
package com.sigma.main.repository

import com.sigma.main.model.account.Account
import com.sigma.main.model.account.AccountResponseModel
import com.sigma.main.model.account.CheckAlarmModel
import com.sigma.main.model.account.EventRequestModel
import com.sigma.main.model.account.FixMemberRequestModel
import com.sigma.main.model.account.JwtResponseModel
import com.sigma.main.model.account.MakeZeroRequestModel
import com.sigma.main.model.account.SpendResponseModel
import com.sigma.main.model.account.RemitRequestModel
import com.sigma.main.model.account.MemberResponseModel
import com.sigma.main.model.account.MessageBodyRequestModel
import com.sigma.main.model.account.SpendCalculateRequestModel
import com.sigma.main.model.account.StatusResponseModel
import com.sigma.main.model.account.WalletResponseModel

interface AccountRepository {

    //SpendList
    suspend fun allSpend(
        walletId: Long
    ): List<List<SpendResponseModel>>

    suspend fun getRecentAccount(
        memberId: String
    ): List<Account>

    suspend fun memberSpend(
        memberName: String,
        walletId: Long
    ): List<SpendResponseModel>

    suspend fun spendCalculate(
        spendCalculateRequestModel: SpendCalculateRequestModel
    ): StatusResponseModel


    //Wallet Management
    suspend fun addMember(
        walletId: Long,
        fixMemberRequestModel: FixMemberRequestModel
    ): StatusResponseModel

    suspend fun deleteMember(
        walletId: Long,
        fixMemberRequestModel: FixMemberRequestModel
    ): StatusResponseModel

    suspend fun remit(
        remitRequestModel: RemitRequestModel
    ): StatusResponseModel

    suspend fun makeZero(
        makeZeroRequestModel: MakeZeroRequestModel
    ): StatusResponseModel


    //Manager
    suspend fun event(
        eventRequestModel: EventRequestModel
    ): StatusResponseModel


    //Member
    suspend fun searchMember(
        memberName: String
    ): List<MemberResponseModel>

    suspend fun authorizationSearchMember(
        authorization: String
    ): MemberResponseModel


    //Alarm
    suspend fun requestAlarm(
        walletId: String,
        messageBodyRequestModel: MessageBodyRequestModel
    ): StatusResponseModel

    suspend fun requestAlarmAll(
        messageBodyRequestModel: MessageBodyRequestModel
    ): StatusResponseModel

    suspend fun checkAlarm(
        memberId: String
    ): CheckAlarmModel



    //Wallet
    suspend fun generate(
        memberId: String,
        accountName: String
    ): Account

    suspend fun getWallet(
        walletId: Long
    ): WalletResponseModel

    suspend fun searchWallet(
        memberId: String
    ): List<WalletResponseModel>

    suspend fun getWallet(
        accountNumber: String
    ): WalletResponseModel

    suspend fun deleteWallet(
        memberId: String,
        walletId: Long,
    ): StatusResponseModel

    suspend fun getAccount(
        accountNumber: String
    ): Account

}
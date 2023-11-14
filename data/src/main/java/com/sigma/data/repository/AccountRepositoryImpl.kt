package com.sigma.data.repository

import android.util.Log
import com.sigma.data.mapper.toDto
import com.sigma.data.mapper.toModel
import com.sigma.data.network.api.AccountApi
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
import com.sigma.main.repository.AccountRepository
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val accountApi: AccountApi
) : AccountRepository {

    override suspend fun remit(remitRequestModel: RemitRequestModel): StatusResponseModel =
        accountApi.remit(remitRequestModel.toDto()).toModel()

    override suspend fun generate(
        memberId: String,
        accountName: String,
    ): Account {
        val data = accountApi.generate(memberId, accountName)
        Log.d(TAG, "generate: $data")
        return data
    }

    override suspend fun getAccount(accountNumber: String): Account
        = accountApi.getAccount(accountNumber)
        
    override suspend fun allSpend(walletId: Long): List<List<SpendResponseModel>> {
        val data = accountApi.allSpend(walletId)
        Log.d(TAG, "allSpend: $data")
        return data.map {
            it.map {
                it.toModel()
            }
        }
    }

    override suspend fun getRecentAccount(memberId: String): List<Account> =
        accountApi.getRecentAccount(memberId)

    override suspend fun getWallet(walletId: Long): WalletResponseModel {
        val data = accountApi.getWallet(walletId)
        Log.d(TAG, "getWallet: $data")
        return data.toModel()
    }

    override suspend fun getWallet(accountNumber: String): WalletResponseModel {
        val data = accountApi.getWallet(accountNumber)
        Log.d(TAG, "getWallet: $data")
        return data.toModel()
    }

    override suspend fun searchWallet(memberId: String): List<WalletResponseModel> {
        val data = accountApi.searchWallet(memberId)
        Log.d(TAG, "searchWallet: $data")
        return data.map { it.toModel() }
    }

    override suspend fun deleteWallet(memberId: String, walletId: Long): StatusResponseModel {
        val data = accountApi.deleteWallet(memberId, walletId)
        Log.d(TAG, "deleteWallet: $data")
        return data.toModel()
    }

    override suspend fun addMember(
        walletId: Long,
        fixMemberRequestModel: FixMemberRequestModel
    ): StatusResponseModel {
        val data = accountApi.addMember(walletId, fixMemberRequestModel.toDto())
        Log.d(TAG, "addMember: $data")
        return data.toModel()
    }

    override suspend fun deleteMember(
        walletId: Long,
        fixMemberRequestModel: FixMemberRequestModel
    ): StatusResponseModel {
        val data = accountApi.deleteMember(walletId,fixMemberRequestModel.toDto())
        Log.d(TAG, "deleteMember: $data")
        return data.toModel()
    }

    override suspend fun makeZero(makeZeroRequestModel: MakeZeroRequestModel): StatusResponseModel {
        val data = accountApi.makeZero(makeZeroRequestModel.toDto())
        Log.d(TAG, "makeZero: $data")
        return data.toModel()
    }

    override suspend fun memberSpend(memberName: String, walletId: Long): List<SpendResponseModel> {
        val data = accountApi.memberSpend(memberName, walletId)
        Log.d(TAG, "memberSpend: $data")
        return data.map { it.toModel() }
    }

    override suspend fun spendCalculate(spendCalculateRequestModel: SpendCalculateRequestModel): StatusResponseModel {
        val data = accountApi.spendCalculate(spendCalculateRequestModel.toDto())
        Log.d(TAG, "spendCalculate: $data")
        return data.toModel()
    }

    override suspend fun event(eventRequestModel: EventRequestModel): StatusResponseModel {
        val data = accountApi.event(eventRequestModel.toDto())
        Log.d(TAG, "event: $data")
        return data.toModel()
    }

    override suspend fun searchMember(memberName: String): List<MemberResponseModel> {
        val data = accountApi.searchMember(memberName)
        Log.d(TAG, "searchMember: $data")
        return data.map {
            it.toModel()
        }
    }

    override suspend fun authorizationSearchMember(authorization: String): MemberResponseModel {
        val data = accountApi.authorizationSearchMember(authorization)
        Log.d(TAG, "authorizationSearchMember: $data")
        return data.toModel()
    }

    override suspend fun requestAlarm(
        walletId: String,
        messageBodyRequestModel: MessageBodyRequestModel
    ): StatusResponseModel {
        val data = accountApi.requestAlarm(walletId, messageBodyRequestModel.toDto())
        Log.d(TAG, "requestAlarm: $data")
        return data.toModel()
    }

    override suspend fun requestAlarmAll(messageBodyRequestModel: MessageBodyRequestModel): StatusResponseModel {
        val data = accountApi.requestAlarmAll(messageBodyRequestModel.toDto())
        Log.d(TAG, "requestAlarmAll: $data")
        return data.toModel()
    }

    override suspend fun checkAlarm(memberId: String): CheckAlarmModel {
        val data = accountApi.checkAlarm(memberId)
        Log.d(TAG, "checkAlarm: $data")
        return data.toModel()
    }


    companion object {
        const val TAG = "AccountRepositoryImpl"
    }

}
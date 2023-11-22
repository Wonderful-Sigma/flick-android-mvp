package com.sigma.data.repository

import com.sigma.data.network.api.AccountApi
import com.sigma.data.network.dto.account.AccountObject
import com.sigma.data.network.dto.account.FixMemberRequest
import com.sigma.data.network.dto.account.RemitRequest
import com.sigma.data.network.dto.account.SpendResponse
import com.sigma.data.network.dto.account.StatusResponse
import com.sigma.data.network.dto.account.WalletResponse
import javax.inject.Inject

class AccountRepository @Inject constructor(
    private val accountApi: AccountApi
) {

    suspend fun remit(remitRequestModel: RemitRequest): StatusResponse =
        accountApi.remit(remitRequestModel)

    suspend fun generate(
        memberId: String,
        accountName: String,
    ): AccountObject
        = accountApi.generate(memberId, accountName)

    suspend fun getAccount(accountNumber: String): AccountObject
        = accountApi.getAccount(accountNumber)

    suspend fun allSpend(walletId: Long): List<List<SpendResponse>> {
        val data = accountApi.allSpend(walletId)
        return data.map { SpendList ->
            SpendList.map {
                it
            }
        }
    }

    suspend fun getRecentAccount(memberId: String): List<AccountObject> =
        accountApi.getRecentAccount(memberId)

    suspend fun getWallet(walletId: Long): WalletResponse =
        accountApi.getWallet(walletId)

    suspend fun getWallet(accountNumber: String): WalletResponse =
        accountApi.getWallet(accountNumber)

    suspend fun searchWallet(memberId: String): List<WalletResponse> {
        val data = accountApi.searchWallet(memberId)
        return data.map { it }
    }

    suspend fun deleteWallet(memberId: String, walletId: Long): StatusResponse =
        accountApi.deleteWallet(memberId, walletId)

    suspend fun addMember(
        walletId: Long,
        fixMemberRequestModel: FixMemberRequest
    ): StatusResponse =
        accountApi.addMember(walletId, fixMemberRequestModel)

//    suspend fun deleteMember(
//        walletId: Long,
//        fixMemberRequestModel: FixMemberRequestModel
//    ): StatusResponseModel {
//        val data = accountApi.deleteMember(walletId,fixMemberRequestModel.toDto())
//        Log.d(TAG, "deleteMember: $data")
//        return data.toModel()
//    }
//
//    suspend fun makeZero(makeZeroRequestModel: MakeZeroRequestModel): StatusResponseModel {
//        val data = accountApi.makeZero(makeZeroRequestModel.toDto())
//        Log.d(TAG, "makeZero: $data")
//        return data.toModel()
//    }
//
//    suspend fun memberSpend(memberName: String, walletId: Long): List<SpendResponseModel> {
//        val data = accountApi.memberSpend(memberName, walletId)
//        Log.d(TAG, "memberSpend: $data")
//        return data.map { it.toModel() }
//    }
//
//    suspend fun spendCalculate(spendCalculateRequestModel: SpendCalculateRequestModel): StatusResponseModel {
//        val data = accountApi.spendCalculate(spendCalculateRequestModel.toDto())
//        Log.d(TAG, "spendCalculate: $data")
//        return data.toModel()
//    }
//
//    suspend fun event(eventRequestModel: EventRequestModel): StatusResponseModel {
//        val data = accountApi.event(eventRequestModel.toDto())
//        Log.d(TAG, "event: $data")
//        return data.toModel()
//    }
//
//    suspend fun searchMember(memberName: String): List<MemberResponseModel> {
//        val data = accountApi.searchMember(memberName)
//        Log.d(TAG, "searchMember: $data")
//        return data.map {
//            it.toModel()
//        }
//    }
//
//    suspend fun authorizationSearchMember(authorization: String): MemberResponseModel {
//        val data = accountApi.authorizationSearchMember(authorization)
//        Log.d(TAG, "authorizationSearchMember: $data")
//        return data.toModel()
//    }
//
//    suspend fun requestAlarm(
//        walletId: String,
//        messageBodyRequestModel: MessageBodyRequestModel
//    ): StatusResponseModel {
//        val data = accountApi.requestAlarm(walletId, messageBodyRequestModel.toDto())
//        Log.d(TAG, "requestAlarm: $data")
//        return data.toModel()
//    }
//
//    suspend fun requestAlarmAll(messageBodyRequestModel: MessageBodyRequestModel): StatusResponseModel {
//        val data = accountApi.requestAlarmAll(messageBodyRequestModel.toDto())
//        Log.d(TAG, "requestAlarmAll: $data")
//        return data.toModel()
//    }
//
//    suspend fun checkAlarm(memberId: String): CheckAlarmModel {
//        val data = accountApi.checkAlarm(memberId)
//        Log.d(TAG, "checkAlarm: $data")
//        return data.toModel()
//    }
//
//
//    companion object {
//        const val TAG = "AccountRepositoryImpl"
//    }

}
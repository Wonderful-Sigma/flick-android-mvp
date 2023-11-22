package com.sigma.flick.feature.user.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sigma.data.network.api.AccountApi
import com.sigma.data.network.api.QRCodeApi
import com.sigma.data.network.api.UserApi
import com.sigma.data.network.dto.account.Account
import com.sigma.data.network.dto.account.MemberSetFirebaseRequest
import com.sigma.data.network.dto.user.UserResponse
import com.sigma.flick.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userApi: UserApi,
    private val qRCodeApi: QRCodeApi,
    private val accountApi: AccountApi
) : BaseViewModel() {

    private var _myInfo = MutableLiveData<UserResponse>()
    val myInfo: LiveData<UserResponse> = _myInfo
    private var _accountData = MutableLiveData<Account>()
    val accountData: LiveData<Account> = _accountData

    private var _jwt = MutableLiveData<String>()
    val jwt: LiveData<String> = _jwt


    fun getUserInfo() = viewModelScope.launch {
        kotlin.runCatching {
            userApi.getUser()
        }.onSuccess {
            Log.d(TAG, "getUser Success!!: $it")
            _myInfo.value = it
        }.onFailure { e ->
            Log.d(TAG, "getUser Failed..:  $e")
        }
    }

    fun generateJwt(walletId: Long) = viewModelScope.launch {
        kotlin.runCatching {
            qRCodeApi.generateJwt(walletId)
        }.onSuccess {
            Log.d(TAG, "generateJwt: success!! $it")
            _jwt.value = it.qrJwt
        }.onFailure { e ->
            Log.d(TAG, "generateJwt: failed.. $e")
        }
    }

    fun getAccount(accountNumber: String) = viewModelScope.launch {
        kotlin.runCatching {
            accountApi.getAccount(accountNumber)
        }.onSuccess {
            Log.d(TAG, "getAccount Success!!: $it")
            _accountData.value = it
        }.onFailure { e ->
            Log.d(TAG, "getAccount Failed..:  $e")
        }
    }

    fun getFCMToken(token: String, uuid: String) = viewModelScope.launch {
        kotlin.runCatching {
            accountApi.setFirebaseToken(uuid, MemberSetFirebaseRequest(token))
        }.onSuccess {
            Log.d(TAG, "getFCMToken Success!!: $it")
        }.onFailure { e ->
            Log.d("정보", "token: $token uuid: $uuid")
            Log.d(TAG, "getFCMToken Failed..:  $e")
        }
    }

    companion object {
        const val TAG = "UserViewModel"
    }
}
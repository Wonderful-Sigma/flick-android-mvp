package com.sigma.flick.feature.user.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sigma.flick.base.BaseViewModel
import com.sigma.main.model.account.Account
import com.sigma.main.model.account.MemberSetFirebaseRequestModel
import com.sigma.main.model.user.UserResponseModel
import com.sigma.main.repository.QRCodeRepository
import com.sigma.main.repository.AccountRepository
import com.sigma.main.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val qrCodeRepository: QRCodeRepository,
    private val accountRepository: AccountRepository
) : BaseViewModel() {

    private var _myInfo = MutableLiveData<UserResponseModel>()
    val myInfo: LiveData<UserResponseModel> = _myInfo
    private var _accountData = MutableLiveData<Account>()
    val accountData: LiveData<Account> = _accountData

    private var _jwt = MutableLiveData<String>()
    val jwt: LiveData<String> = _jwt


    fun getUserInfo() = viewModelScope.launch {
        kotlin.runCatching {
            userRepository.getUser()
        }.onSuccess {
            Log.d(TAG, "getUser Success!!: $it")
            _myInfo.value = it
        }.onFailure { e ->
            Log.d(TAG, "getUser Failed..:  $e")
        }
    }

    fun generateJwt(walletId: Long) = viewModelScope.launch {
        kotlin.runCatching {
            qrCodeRepository.generateJwt(walletId)
        }.onSuccess {
            Log.d(TAG, "generateJwt: success!! $it")
            _jwt.value = it.jwt
        }.onFailure { e ->
            Log.d(TAG, "generateJwt: failed.. $e")
        }
    }

    fun getAccount(accountNumber: String) = viewModelScope.launch {
        kotlin.runCatching {
            accountRepository.getAccount(accountNumber)
        }.onSuccess {
            Log.d(TAG, "getAccount Success!!: $it")
            _accountData.value = it
        }.onFailure { e ->
            Log.d(TAG, "getAccount Failed..:  $e")
        }
    }

    fun getFCMToken(token: String, uuid: String) = viewModelScope.launch {
        kotlin.runCatching {
            accountRepository.setFirebaseToken(uuid, MemberSetFirebaseRequestModel(token))
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
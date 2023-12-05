package com.wonderfulsigma.flick.feature.send.viewmodel

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sigma.data.network.api.AccountApi
import com.sigma.data.network.api.SpendListApi
import com.sigma.data.network.dto.account.Account
import com.sigma.data.network.dto.account.MessageBodyRequest
import com.sigma.data.network.dto.account.RemitRequest
import com.wonderfulsigma.flick.base.BaseViewModel
import com.wonderfulsigma.flick.feature.send.state.AccountCheckState
import com.wonderfulsigma.flick.feature.send.state.AccountNumberState
import com.wonderfulsigma.flick.feature.send.state.SendState
import com.wonderfulsigma.flick.utils.fadeIn
import com.wonderfulsigma.flick.utils.fastFadeOut
import com.wonderfulsigma.flick.utils.slideDown
import com.wonderfulsigma.flick.utils.slideUp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SendViewModel @Inject constructor(
    private val accountApi: AccountApi,
    private val spendListApi: SpendListApi
) : BaseViewModel() {

    private var _accountNumberState = MutableSharedFlow<AccountNumberState>()
    val accountNumberState: SharedFlow<AccountNumberState> = _accountNumberState

    private var _accountCheckState = MutableSharedFlow<AccountCheckState>()
    val accountCheckState: SharedFlow<AccountCheckState> = _accountCheckState

    private var _sendState = MutableSharedFlow<SendState>()
    val sendState: SharedFlow<SendState> = _sendState

    private var _sendCoin = MutableLiveData<String>()
    val sendCoin: LiveData<String> = _sendCoin

    private var _recentSpendList = MutableLiveData<List<Account>>()
    val recentSpendList: LiveData<List<Account>> = _recentSpendList

    private var _depositAccountId = MutableLiveData<Long>()
    val depositAccountId: LiveData<Long> = _depositAccountId
    private var _depositAccountName = MutableLiveData<String>()
    val depositAccountName: LiveData<String> = _depositAccountName
    private var _depositAccountNumber = MutableLiveData<String>()
    val depositAccountNumber: LiveData<String> = _depositAccountNumber

    private var _sendCoinIsFilled = MutableLiveData<Boolean>()
    private var _isSent = MutableLiveData<Boolean>()

    val sendCoinIsFilled: LiveData<Boolean> = _sendCoinIsFilled
    val isSent: LiveData<Boolean> = _isSent


    init {
        _sendCoin.value = ""
        _sendCoinIsFilled.value = false
        _isSent.value = false
        _depositAccountNumber.value = ""
    }

    fun checkAccount(accountNumber: String) = viewModelScope.launch {
        kotlin.runCatching {
            accountApi.getAccount(accountNumber)
        }.onSuccess {
            Log.d(TAG, "checkAccount Success!! $it")
            _accountCheckState.emit(AccountCheckState(isSuccess = true))
        }.onFailure { e ->
            Log.d(TAG, "checkAccount Failed.. $e")
            _accountCheckState.emit(AccountCheckState(error = "$e"))
        }
    }

    fun getAccount(accountNumber: String) = viewModelScope.launch {
        kotlin.runCatching {
            accountApi.getAccount(accountNumber)
        }.onSuccess {
            Log.d(TAG, "getAccount Success!! $it")
            _depositAccountId.value = it.id
            _depositAccountName.value = it.name
            _accountNumberState.emit(AccountNumberState(isSuccess = true))
        }.onFailure { e ->
            Log.d(TAG, "getAccount Failed.. $e")
            _accountNumberState.emit(AccountNumberState(error = "$e"))
        }
    }

    fun remit(remitRequestModel: RemitRequest) = viewModelScope.launch {
        kotlin.runCatching {
            accountApi.remit(remitRequestModel)
        }.onSuccess {
            when (it.status) {
                200 -> {
                    Log.d(TAG, "remit: $it")
                    _sendState.emit(SendState(isSuccess = true))
                }

                400 -> {
                    Log.d(TAG, "remit: $it")
                    _sendState.emit(SendState(error = it.message))
                }
            }
        }.onFailure { e ->
            Log.d(TAG, "remit: $e")
            _sendState.emit(SendState(error = "$e"))
        }
    }

    fun getRecentSpendList(memberId: String) = viewModelScope.launch {
        kotlin.runCatching {
            spendListApi.getRecentSpendList(memberId)
        }.onSuccess {
            Log.d(TAG, "getRecentSpendList Success!! $it")
            _recentSpendList.value = it
        }.onFailure { e ->
            Log.d(TAG, "getRecentSpendList Failed.. $e")
        }
    }


    fun postAlarm(memberId: String, messageBody: MessageBodyRequest) =
        viewModelScope.launch {
            kotlin.runCatching {
                accountApi.requestAlarm(memberId, messageBody)
            }.onSuccess {
                when (it.status) {
                    202 -> {
                        Log.d(TAG, "postAlarm Success!! $it")
                    }

                    else -> {
                        Log.d(TAG, "postAlarm Failed.. ${it.status}")
                    }
                }
            }.onFailure { e ->
                Log.d(TAG, "postAlarm Failed.. $e")
            }
        }


    /* sendCoin */

    fun setCoin(number: String) {
        _sendCoin.value = number
    }

    fun plusCoin(number: String) {
        if (sendCoin.value.toString().length < 9) {
            _sendCoin.value = sendCoin.value + number
        }
    }

    fun backSpaceCoin() {
        when (val length = sendCoin.value?.length!!) {
            0, 1 -> _sendCoin.value = ""
            else -> _sendCoin.value = sendCoin.value?.substring(0, length - 1)
        }
    }

    /* sendCoinIsFilled */

    fun setSendCoinIsFilledToFalse(boolean: Boolean) {
        _sendCoinIsFilled.value = boolean
    }

    fun ifIsNotFilledSendCoin(
        context: Context,
        tvCoin: TextView,
        tvHowMuchSend: TextView,
        btnDecide: Button
    ) {
        if (_sendCoinIsFilled.value == false) { // sendCoin이 채워져 있지 않은 상태였다면
            Log.d("TEST", "ifIsNotFilledSendCoin: if is false")
            tvCoin.visibility = View.VISIBLE
            tvHowMuchSend.fastFadeOut(context)
            btnDecide.slideUp(context)

            _sendCoinIsFilled.value = true
        }
    }

    fun ifIsFilledSendCoin(
        context: Context,
        tvCoin: TextView,
        tvHowMuchSend: TextView,
        btnDecide: Button
    ) {
        if (_sendCoinIsFilled.value == true) {
            tvCoin.visibility = View.INVISIBLE
            tvHowMuchSend.fadeIn(context)
            btnDecide.slideDown(context)

            _sendCoinIsFilled.value = false
        }
    }

    /* isSent */

    fun setIsSent(boolean: Boolean) {
        _isSent.value = boolean
    }


    /* fun setRemittanceAccount(accountNumber : Long) {
        remittanceAccount = accountNumber
    } */

    fun setDepositAccountNumber(accountNumber: String) {
        _depositAccountNumber.value = accountNumber
    }

    companion object {
        const val TAG = "SendViewModel"
    }

//    fun resetAccountCheckState() {
//        _accountCheckState = MutableSharedFlow<AccountNumberState>()
//    }
}


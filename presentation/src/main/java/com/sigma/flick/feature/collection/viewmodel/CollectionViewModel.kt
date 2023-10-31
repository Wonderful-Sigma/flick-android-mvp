package com.sigma.flick.feature.collection.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sigma.flick.base.BaseViewModel
import com.sigma.flick.feature.collection.state.PaymentState
import com.sigma.main.model.account.Account
import com.sigma.main.model.account.SpendResponseModel
import com.sigma.main.repository.AccountRepository
import com.sigma.main.repository.QRCodeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CollectionViewModel @Inject constructor(
    private val accountRepository: AccountRepository,
    private val qrCodeRepository: QRCodeRepository
) : BaseViewModel() {

    private var _accountData = MutableLiveData<Account>()
    val accountData: LiveData<Account> = _accountData
    private var _spendList = MutableLiveData<List<List<SpendResponseModel>>>()
    val spendList: LiveData<List<List<SpendResponseModel>>> = _spendList

    /** Products */
    private var _clickProductPrice = MutableLiveData<Long>()
    val clickProductPrice: LiveData<Long> = _clickProductPrice

    /** QR Payment Handling */
    private val _paymentState = MutableSharedFlow<PaymentState>()
    val paymentState: SharedFlow<PaymentState> = _paymentState
    private var _remittanceAccount = MutableLiveData<Long>()
    val remittanceAccount: LiveData<Long> = _remittanceAccount

    fun generateCollectionAccount(memberId : String, accountName: String) = viewModelScope.launch {
        kotlin.runCatching {
            accountRepository.generate(memberId, accountName)
        }.onSuccess {
            Log.d(TAG, "generate: $it")
            _accountData.value = it
        }.onFailure { e ->
            Log.d(TAG, "generate: $e")
        }
    }


    fun getSpend(walletId: Long) = viewModelScope.launch {
        kotlin.runCatching {
            accountRepository.allSpend(walletId)
        }.onSuccess {
            _spendList.value = it
        }.onFailure { e ->
            Log.d(TAG, "$e")
        }
    }

    fun decodingJwt(jwt: String) = viewModelScope.launch {
        kotlin.runCatching {
            qrCodeRepository.decodingJwt(jwt)
        }.onSuccess {
            Log.d(TAG, "decodingJwt: success!! $it")
            _paymentState.emit(PaymentState(isSuccess = true))
            _remittanceAccount.value = it.id
        }.onFailure { e ->
            Log.d(TAG, "decodingJwt: failed.. $e")
            _paymentState.emit(PaymentState(error = "$e"))
        }

    }

    fun setProductPrice(price: Long) {
        _clickProductPrice.value = price
    }

    companion object {
        const val TAG = "CollectionViewModel"
    }
}
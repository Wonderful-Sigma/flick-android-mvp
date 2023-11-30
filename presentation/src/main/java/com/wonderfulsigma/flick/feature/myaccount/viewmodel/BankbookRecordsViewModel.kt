package com.wonderfulsigma.flick.feature.myaccount.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sigma.data.network.api.AccountApi
import com.sigma.data.network.dto.account.SpendResponse
import com.sigma.data.network.dto.account.WalletResponse
import com.wonderfulsigma.flick.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BankbookRecordsViewModel @Inject constructor(
    private val accountApi: AccountApi
) : BaseViewModel() {

    private val _spendList = MutableLiveData<List<List<SpendResponse>>>()
    val spendList: LiveData<List<List<SpendResponse>>>
        get() = _spendList

    private val _accountData = MutableLiveData<WalletResponse>()
    val accountData: LiveData<WalletResponse>
        get() = _accountData


    fun allSpend(walletId: Long) = viewModelScope.launch {
        kotlin.runCatching {
            accountApi.allSpend(walletId)
        }.onSuccess {
            Log.d(TAG, "allSpend: $it")
            _spendList.value = it
        }.onFailure { e ->
            Log.d(TAG, "allSpend: $e")
        }
    }

    fun getWallet(walletId: Long) = viewModelScope.launch {
        kotlin.runCatching {
            accountApi.getWallet(walletId)
        }.onSuccess {
            _accountData.value = it
            Log.d(TAG, "getWallet: $it")
        }.onFailure { e ->
            Log.d(TAG, "getWallet: $e")
        }
    }

    companion object{
        const val TAG = "BankbookRecordsViewModel"
    }

}
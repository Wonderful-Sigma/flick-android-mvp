package com.sigma.flick.feature.myaccount.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sigma.data.network.dto.account.SpendResponse
import com.sigma.data.network.dto.account.WalletResponse
import com.sigma.data.repository.AccountRepository
import com.sigma.flick.base.BaseViewModel
import com.sigma.flick.feature.tabs.home.viewmodel.HomeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BankbookRecordsViewModel @Inject constructor(
    private val accountRepository: AccountRepository
) : BaseViewModel() {

    private val _spendList = MutableLiveData<List<List<SpendResponse>>>()
    val spendList: LiveData<List<List<SpendResponse>>>
        get() = _spendList

    private val _accountData = MutableLiveData<WalletResponse>()
    val accountData: LiveData<WalletResponse>
        get() = _accountData


    fun allSpend(walletId: Long) = viewModelScope.launch {
        kotlin.runCatching {
            accountRepository.allSpend(walletId)
        }.onSuccess {
            Log.d(TAG, "allSpend: $it")
            _spendList.value = it
        }.onFailure { e ->
            Log.d(TAG, "allSpend: $e")
        }
    }

    fun getWallet(walletId: Long) = viewModelScope.launch {
        kotlin.runCatching {
            accountRepository.getWallet(walletId)
        }.onSuccess {
            _accountData.value = it
            Log.d(HomeViewModel.TAG, "getWallet: $it")
        }.onFailure { e ->
            Log.d(HomeViewModel.TAG, "getWallet: $e")
        }
    }

    companion object{
        const val TAG = "BankbookRecordsViewModel"
    }

}
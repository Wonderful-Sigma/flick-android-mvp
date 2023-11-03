package com.sigma.flick.feature.myaccount.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sigma.flick.base.BaseViewModel
import com.sigma.flick.feature.tabs.home.viewmodel.HomeViewModel
import com.sigma.main.model.account.SpendResponseModel
import com.sigma.main.model.account.WalletResponseModel
import com.sigma.main.repository.AccountRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BankbookRecordsViewModel @Inject constructor(
    private val accountRepository: AccountRepository
) : BaseViewModel() {

    private val _spendList = MutableLiveData<List<List<SpendResponseModel>>>()
    val spendList: LiveData<List<List<SpendResponseModel>>>
        get() = _spendList

    private val _accountData = MutableLiveData<WalletResponseModel>()
    val accountData: LiveData<WalletResponseModel>
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
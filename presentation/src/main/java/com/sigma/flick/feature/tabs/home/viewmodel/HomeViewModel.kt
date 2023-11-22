package com.sigma.flick.feature.tabs.home.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sigma.data.network.dto.account.WalletResponse
import com.sigma.data.repository.AccountRepository
import com.sigma.flick.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val accountRepository: AccountRepository
) : BaseViewModel() {

    private val _accountPublic = MutableLiveData<MutableList<WalletResponse>>()
    val accountPublic: LiveData<MutableList<WalletResponse>>
        get() = _accountPublic

    private val _accountPersonal = MutableLiveData<WalletResponse>()
    val accountPersonal: LiveData<WalletResponse>
        get() = _accountPersonal


    //
    fun searchWallet(memberId: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                accountRepository.searchWallet(memberId)
            }.onSuccess {
                division(it)
                Log.d(TAG, "searchWallet: $it")
            }.onFailure { e ->
                Log.d(TAG, "searchWallet: $e")
            }
        }
    }

    private fun division(data: List<WalletResponse>){
        data.map {
            if(it.accountType == "PUBLIC"){
                accountPublic.value?.add(it)
            } else{
                _accountPersonal.value = it
            }
        }
    }

    companion object{
        const val TAG = "HomeViewModel"
    }
}
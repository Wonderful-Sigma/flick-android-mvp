package com.sigma.flick.feature.home.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sigma.flick.base.BaseViewModel
import com.sigma.main.model.account.WalletResponseModel
import com.sigma.main.repository.AccountRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
@Inject constructor(private val accountRepository: AccountRepository) : BaseViewModel() {

    private val _accountPublic = MutableLiveData<MutableList<WalletResponseModel>>()
    val accountPublic: LiveData<MutableList<WalletResponseModel>>
        get() = _accountPublic

    private val _accountPersonal = MutableLiveData<WalletResponseModel>()
    val accountPersonal: LiveData<WalletResponseModel>
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

    private fun division(data: List<WalletResponseModel>){
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
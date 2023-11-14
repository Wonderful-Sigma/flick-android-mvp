package com.sigma.flick.feature.start

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sigma.data.network.api.MemberApi
import com.sigma.flick.base.BaseViewModel
import com.sigma.flick.utils.HiltApplication
import com.sigma.main.model.dauth.DauthRequestModel
import com.sigma.main.repository.MemberRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kr.hs.dgsw.smartschool.dodamdodam.dauth.DAuth
import javax.inject.Inject

@HiltViewModel
class StartViewModel @Inject constructor(
    private val memberRepository: MemberRepository
): BaseViewModel() {

    private var _autoLogin = MutableLiveData(HiltApplication.prefs.autoLogin)
    val autoLogin: LiveData<Boolean> = _autoLogin

    fun getCode(context: Context) {
        DAuth.getCode(context, { code ->
            Log.d(TAG, "getCodeSuccess!! $code")
            login(DauthRequestModel(code))
            Toast.makeText(context, "로그인 되었어요, 잠시만 기다려주세요", Toast.LENGTH_SHORT).show()
        }, { e ->
            Log.d(TAG, "getCodeFailed.. $e")
            Toast.makeText(context, "아이디나 비밀번호를 다시 확인해주세요", Toast.LENGTH_SHORT).show()
        })
    }

    private fun login(dauthRequestDto: DauthRequestModel) = viewModelScope.launch {
        kotlin.runCatching {
            memberRepository.login(dauthRequestDto)
        }.onSuccess {
            Log.d(TAG, "LoginSuccess! $it")

            HiltApplication.prefs.autoLogin = true
            _autoLogin.value = true
            HiltApplication.prefs.accessToken = it.accessToken
            HiltApplication.prefs.refreshToken = it.refreshToken
        }.onFailure { e ->
            Log.d(TAG, "LoginFailed.. $e")
        }
    }

    /** TEST */
    fun getNewAccessToken(refreshToken: String) = viewModelScope.launch {
        kotlin.runCatching {
            memberRepository.getAccessToken(refreshToken)
        }.onSuccess {
            Log.d(TAG, "SUCCESS! $it")
//            HiltApplication.prefs.autoLogin = true
//            _autoLogin.value = true
//            HiltApplication.prefs.accessToken = it.accessToken
//            HiltApplication.prefs.refreshToken = it.refreshToken
        }.onFailure { e ->
            Log.d(TAG, "LoginFailed.. $e")
        }
    }

    companion object {
        private const val TAG = "StartViewModel"
    }
}
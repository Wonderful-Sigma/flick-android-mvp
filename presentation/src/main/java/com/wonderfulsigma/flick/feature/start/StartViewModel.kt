package com.wonderfulsigma.flick.feature.start

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sigma.data.network.api.UserApi
import com.sigma.data.network.dto.dauth.DauthRequest
import com.wonderfulsigma.flick.base.BaseViewModel
import com.wonderfulsigma.flick.utils.HiltApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kr.hs.dgsw.smartschool.dodamdodam.dauth.DAuth
import javax.inject.Inject

@HiltViewModel
class StartViewModel @Inject constructor(
//    private val dauthApi: DauthApi,
    private val userApi: UserApi,
): BaseViewModel() {

    private var _autoLogin = MutableLiveData(HiltApplication.prefs.autoLogin)
    val autoLogin: LiveData<Boolean> = _autoLogin

    fun getCode(context: Context) {
        DAuth.getCode(context, { code ->
            Log.d(TAG, "getCodeSuccess!! $code")
            login(DauthRequest(code))
            Toast.makeText(context, "로그인 되었어요, 잠시만 기다려주세요", Toast.LENGTH_SHORT).show()
        }, { e ->
            Log.d(TAG, "getCodeFailed.. $e")
            Toast.makeText(context, "아이디나 비밀번호를 다시 확인해주세요", Toast.LENGTH_SHORT).show()
        })
    }

//    fun dauthLogin(dauthLoginRequest: DauthLoginRequest) = viewModelScope.launch {
//        kotlin.runCatching {
//            dauthApi.dauthLogin(dauthLoginRequest)
//        }.onSuccess {
//            Log.d(TAG, "dauthLogin: SUCCESS $it")
//        }.onFailure { e ->
//            Log.d(TAG, "dauthLogin: FAILED $e")
//        }
//    }

    private fun login(dauthRequestDto: DauthRequest) = viewModelScope.launch {
        kotlin.runCatching {
            userApi.login(dauthRequestDto)
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

    companion object {
        private const val TAG = "StartViewModel"
    }
}
package com.sigma.flick.di.Authenticator

import android.util.Log
import com.ggd.network.api.AuthApi
import com.ggd.repository.AuthRepository
import com.ggd.zendee.di.utils.BASE_URL
import com.ggd.zendee.utils.HiltApplication
import com.sigma.data.network.api.UserApi
import com.sigma.flick.utils.BASE_URL
import com.sigma.flick.utils.HiltApplication
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.internal.ComponentEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.Authenticator
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import javax.inject.Inject

// todo 1. userApi에 Url이 ToKen으로 작성된 묹[
// todo 2. 스웨거에 작성된 response값이 string으로만 되어있음;;

class TokenAuthenticator: Authenticator {

    private val refreshRetrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(
            OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build()
        )
        .addConverterFactory(GsonConverterFactory.create())
//        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    private val userService = refreshRetrofit.create(UserApi::class.java)

    override fun authenticate(route: Route?, response: Response): Request? {
        Log.i("Authenticator", response.toString())
        Log.i("Authenticator", "토큰 재발급 시도")
        var newAccessToken = ""
        var request:Request? = null

        GlobalScope.launch {
            try {
                Log.i("Authenticator", "orgToken: ${HiltApplication.prefs.accessToken}")

//                newAccessToken = userService.getAccessToken(
//                    HiltApplication.prefs.refreshToken
//                )

                Log.i("Authenticator", "토큰 재발급 성공 : $newAccessToken")
                HiltApplication.prefs.deleteAccessToken()
                HiltApplication.prefs.accessToken = newAccessToken
                request = response.request.newBuilder()
                    .removeHeader("accessToken").apply {
                        addHeader("accessToken", newAccessToken)
                    }.build()
                Log.i("Authenticator", "accessToken in header ${request!!.headers["accessToken"]}")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return request
    }
}
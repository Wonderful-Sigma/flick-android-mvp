package com.sigma.flick.di.authenticator

import android.util.Log
import com.google.gson.GsonBuilder
import com.sigma.data.network.api.UserApi
import com.sigma.data.network.dto.user.NewAccessTokenResponseDto
import com.sigma.flick.utils.HiltApplication
import com.sigma.main.model.user.UserResponseModel
import kotlinx.coroutines.runBlocking
import kr.hs.dgsw.smartschool.dodamdodam.dauth.model.response.LoginResponse
import okhttp3.Authenticator
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AuthAuthenticator: Authenticator {

    private val preferenceManager = HiltApplication.prefs

    override fun authenticate(route: Route?, response: Response): Request? {
        val token = preferenceManager.refreshToken

        if (token == null) {
            Log.i("FAILED", "NULL : refreshToken is null ")
        }

        return runBlocking {
            val newToken = getNewToken(token).newAccess

            newToken?.let {
                preferenceManager.accessToken = it
                response.request.newBuilder()
                    .header("Authorization", "Bearer $it")
                    .build()
            }
        }
    }

    private suspend fun getNewToken(refreshToken: String): NewAccessTokenResponseDto {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()

        val gson = GsonBuilder().setLenient().create()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://jwt-test-api.onrender.com/api/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
        val userService = retrofit.create(UserApi::class.java)
        return userService.getAccessToken(refreshToken)
    }
}
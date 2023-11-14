package com.sigma.flick.di.authenticator

import android.util.Log
import com.google.gson.GsonBuilder
import com.sigma.data.network.api.MemberApi
import com.sigma.data.network.api.QRCodeApi
import com.sigma.data.network.dto.user.NewAccessTokenResponseDto
import com.sigma.flick.utils.BASE_URL
import com.sigma.flick.utils.HiltApplication
import dagger.Provides
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

class AuthAuthenticator: Authenticator {

    private val memberApi = createMemberApi()

    override fun authenticate(route: Route?, response: Response): Request? {
        return runBlocking {
            var newToken = ""
            kotlin.runCatching {
                newToken = memberApi.getAccessToken(HiltApplication.prefs.refreshToken).newAccess
            }.onSuccess {
                HiltApplication.prefs.accessToken = newToken
                response.request.newBuilder()
                    .header("Authorization","Bearer $newToken")
                    .build()
            }.onFailure {
                Log.i("ERROR", "authenticate: getAccessToken is FAILED..")
            }
            null
        }
    }

    private fun createMemberApi(): MemberApi {
        val okHttpClient = OkHttpClient().newBuilder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
            .build()

        val gson = GsonBuilder().setLenient().create()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()

        return retrofit.create(MemberApi::class.java)
    }

}
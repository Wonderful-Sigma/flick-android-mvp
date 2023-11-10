package com.sigma.flick.di.authenticator

import com.google.gson.GsonBuilder
import com.sigma.data.network.api.UserApi
import com.sigma.flick.utils.BASE_URL
import com.sigma.flick.utils.HiltApplication
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/* todo: 넣어서 다시 실행 시켜야 함 */

class TokenInterceptor: Interceptor {

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()

    private val gson = GsonBuilder().setLenient().create()

    private val refreshRetrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    private val userApi = refreshRetrofit.create(UserApi::class.java)

    private val preferenceManager = HiltApplication.prefs

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val accessToken = preferenceManager.accessToken

        if (accessToken != null) {
            val refreshToken = preferenceManager.refreshToken

            // Make the token refresh request
            val refreshedToken = runBlocking {
                val response = userApi.getAccessToken(refreshToken)
                // Update the refreshed access token and its expiration time in the session
                preferenceManager.deleteAccessToken()
                preferenceManager.accessToken = response.newAccess
                response.newAccess
            }

            if (refreshedToken != null) {
                // Create a new request with the refreshed access token
                val newRequest = originalRequest.newBuilder()
                    .header("Authorization", "Bearer $refreshedToken") // todo : 이거 붙이는 거 맞나?
                    .build()

                // Retry the request with the new access token
                return chain.proceed(newRequest)
            }
        }
        // Add the access token to the request header
        val authorizedRequest = originalRequest.newBuilder()
            .header("Authorization", "Bearer $accessToken")
            .build()

        return chain.proceed(authorizedRequest)
    }
}
package com.wonderfulsigma.flick.di.module

import com.google.gson.GsonBuilder
import com.sigma.data.network.api.AccountApi
import com.sigma.data.network.api.QRCodeApi
import com.sigma.data.network.api.SpendListApi
import com.sigma.data.network.api.UserApi
import com.wonderfulsigma.flick.di.authenticator.AuthAuthenticator
import com.wonderfulsigma.flick.utils.BASE_URL
import com.wonderfulsigma.flick.utils.DAUTH_BASE_URL
import com.wonderfulsigma.flick.utils.HiltApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideAccountApi(retrofit: Retrofit): AccountApi =
        retrofit.create(AccountApi::class.java)

    @Provides
    @Singleton
    fun provideUserApi(retrofit: Retrofit): UserApi =
        retrofit.create(UserApi::class.java)

    @Provides
    @Singleton
    fun provideSpendListApi(retrofit: Retrofit): SpendListApi =
        retrofit.create(SpendListApi::class.java)

    @Provides
    @Singleton
    fun provideQRCodeApi(retrofit: Retrofit): QRCodeApi =
        retrofit.create(QRCodeApi::class.java)


    /* Retrofit Object 생성 */

    private var gson = GsonBuilder().setLenient().create()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
    }


    /* OkHttp로 세부적인 네트워크 구성요소를 설정 */

    @Singleton
    @Provides
    fun provideOkHttpClient(
        headerInterceptor: Interceptor,
        loggerInterceptor: HttpLoggingInterceptor,
        authAuthenticator: AuthAuthenticator,
    ): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient().newBuilder()
        okHttpClientBuilder.connectTimeout(60, TimeUnit.SECONDS)
        okHttpClientBuilder.readTimeout(60, TimeUnit.SECONDS)
        okHttpClientBuilder.writeTimeout(60, TimeUnit.SECONDS)
        okHttpClientBuilder.addInterceptor(loggerInterceptor)
        okHttpClientBuilder.addInterceptor(headerInterceptor)
        okHttpClientBuilder.authenticator(authAuthenticator)
        return okHttpClientBuilder.build()
    }

    @Provides
    @Singleton
    fun provideAuthAuthenticator(): AuthAuthenticator =
        AuthAuthenticator()

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

    @Provides
    @Singleton
    fun provideHeaderInterceptor() = Interceptor { chain ->
        with(chain) {
            val newRequest = request().newBuilder()
                .addHeader("Authorization", "Bearer" + HiltApplication.prefs.accessToken)
                .build()
            proceed(newRequest)
        }
    }
}
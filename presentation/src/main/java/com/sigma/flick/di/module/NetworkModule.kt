package com.sigma.flick.di.module

import com.google.gson.GsonBuilder
import com.sigma.data.network.api.AccountApi
import com.sigma.data.network.api.MemberApi
import com.sigma.data.network.api.QRCodeApi
import com.sigma.data.network.api.SpendListApi
import com.sigma.flick.di.authenticator.AuthAuthenticator
import com.sigma.flick.utils.BASE_URL
import com.sigma.flick.utils.HiltApplication
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
    fun provideDauthApi(retrofit: Retrofit): MemberApi =
        retrofit.create(MemberApi::class.java)

    @Provides
    @Singleton
    fun provideAccountApi(retrofit: Retrofit): AccountApi =
        retrofit.create(AccountApi::class.java)


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

//    @Singleton
//    @Provides
//    fun provideAuthInterceptor(): AuthInterceptor = AuthInterceptor()

    @Singleton
    @Provides
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
                .addHeader("Authorization", "Bearer ${HiltApplication.prefs.accessToken}")
//                .addHeader("Authorization", "Bearer eyJKV1QiOiJBQ0NFU1NfVE9LRU4iLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI3NzJhZDBkMy0xYTRhLTRhNTUtYmE1MC1jNmI3ZWNlZDFmYjAiLCJuYW1lIjoi7KGw7Iq57JmEIiwicnVsZSI6IlNUVURFTlQiLCJpYXQiOjE2OTk5NjU3MjYsImV4cCI6MTY5OTk2NTczNn0.mgxPjBM64w7C_mb4I8W_Jx8us365i5-N2HKMrKEgmAaw4MH1_PJ2rkm3YYEGdF-LESrAGwcZodO5t-3PaRrviA")
                .build()
            proceed(newRequest)
        }
    }

}
package com.sigma.flick.di.module

import com.sigma.data.repository.AccountRepositoryImpl
import com.sigma.data.repository.MemberRepositoryImpl
import com.sigma.data.repository.QRCodeRepositoryImpl
import com.sigma.main.repository.AccountRepository
import com.sigma.main.repository.MemberRepository
import com.sigma.main.repository.QRCodeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideAccountRepository(accountRepositoryImpl: AccountRepositoryImpl) : AccountRepository {
        return accountRepositoryImpl
    }

    @Provides
    @Singleton
    fun provideUserRepository(
        memberRepositoryImpl: MemberRepositoryImpl
    ) : MemberRepository = memberRepositoryImpl

    @Provides
    @Singleton
    fun provideQRCodeRepository(impl: QRCodeRepositoryImpl): QRCodeRepository =
        impl


}
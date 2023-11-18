package com.sigma.flick.di.module

import com.sigma.data.network.dto.account.Account
import com.sigma.data.repository.AccountRepository
import com.sigma.data.repository.MemberRepository
import com.sigma.data.repository.QRCodeRepository
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
    fun provideAccountRepository(
        accountRepository: AccountRepository
    ): AccountRepository = accountRepository

    @Provides
    @Singleton
    fun provideUserRepository(
        memberRepository: MemberRepository
    ) : MemberRepository = memberRepository

    @Provides
    @Singleton
    fun provideQRCodeRepository(
        impl: QRCodeRepository
    ): QRCodeRepository = impl


}
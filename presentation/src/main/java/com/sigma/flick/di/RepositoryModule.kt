package com.sigma.flick.di

import com.sigma.data.repository.DauthRepositoryImpl
import com.sigma.data.repository.AccountRepositoryImpl
import com.sigma.data.repository.QRCodeRepositoryImpl
import com.sigma.data.repository.SpendListRepositoryImpl
import com.sigma.data.repository.UserRepositoryImpl
import com.sigma.main.repository.DauthRepository
import com.sigma.main.repository.AccountRepository
import com.sigma.main.repository.QRCodeRepository
import com.sigma.main.repository.SpendListRepository
import com.sigma.main.repository.UserRepository
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
    fun provideDauthRepository(dauthRepositoryImpl: DauthRepositoryImpl) : DauthRepository {
        return dauthRepositoryImpl
    }

    @Provides
    @Singleton
    fun provideAccountRepository(accountRepositoryImpl: AccountRepositoryImpl) : AccountRepository {
        return accountRepositoryImpl
    }

    @Provides
    @Singleton
    fun provideUserRepository(userRepositoryImpl: UserRepositoryImpl) : UserRepository
        = userRepositoryImpl

    @Provides
    @Singleton
    fun provideSpendListRepository(spendListRepositoryImpl: SpendListRepositoryImpl) : SpendListRepository
        = spendListRepositoryImpl

    @Provides
    @Singleton
    fun provideQRCodeRepository(impl: QRCodeRepositoryImpl): QRCodeRepository =
        impl


}
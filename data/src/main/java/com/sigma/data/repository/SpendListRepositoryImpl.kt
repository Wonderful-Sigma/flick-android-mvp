package com.sigma.data.repository

import com.sigma.data.mapper.toModel
import com.sigma.data.network.api.SpendListApi
import com.sigma.main.model.account.Account
import com.sigma.main.repository.SpendListRepository
import javax.inject.Inject

class SpendListRepositoryImpl @Inject constructor(
    private val spendListApi: SpendListApi
): SpendListRepository {

    override suspend fun getRecentSpendList(memberId: String): List<Account>
        = spendListApi.getRecentSpendList(memberId)

}
package com.sigma.main.repository

import com.sigma.main.model.account.Account

interface SpendListRepository {

    suspend fun getRecentSpendList(
        memberId: String
    ): List<Account>

}
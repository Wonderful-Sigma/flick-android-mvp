package com.sigma.data.network.api

import com.sigma.data.network.dto.account.Account
import retrofit2.http.GET
import retrofit2.http.Path

interface SpendListApi {

    @GET("/api/spend/recent/remit/{memberId}")
    suspend fun getRecentSpendList(
        @Path("memberId") memberId: String
    ): List<Account>

}
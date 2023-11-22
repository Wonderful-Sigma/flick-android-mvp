package com.sigma.data.network.dto.account

data class SpendCalculateResponse(
    val allList: List<List<SpendResponse>>,
    val incomeList: List<List<SpendResponse>>,
    val expenditureList: List<List<SpendResponse>>,
    val loss: Long,
    val benefit: Long
)

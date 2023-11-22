package com.sigma.main.model.account

data class SpendCalculateResponseModel(
    val allList: List<List<SpendResponseModel>>,
    val incomeList: List<List<SpendResponseModel>>,
    val expenditureList: List<List<SpendResponseModel>>,
    val loss: Long,
    val benefit: Long
)

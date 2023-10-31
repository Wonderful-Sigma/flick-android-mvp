package com.sigma.main.model.account

data class GenerateResponseModel(
    val id: Long,
    val number: String,
    val name: String,
    val money: Long,
    val managerId: String,
    val accountType: String,
    val accountNumber: Long,
    val spendLists: List<SpendResponseModel>
)
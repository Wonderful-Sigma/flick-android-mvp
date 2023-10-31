package com.sigma.main.model.account

data class AccountResponseModel(
    val id: Long,
    val number: String,
    val name: String,
    val money: Long,
    val managerId: String,
    val accountType: String,
    val spendLists: List<Account>
)
package com.sigma.main.model.account
data class SpendResponseModel(
    val targetMember: String,
    val targetAccount: Long,
    val money: Long,
    val spendType: String,
    val balance: Long,
    val createdDate: String,
)
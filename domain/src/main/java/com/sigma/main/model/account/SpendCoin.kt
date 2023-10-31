package com.sigma.main.model.account


data class SpendCoin(
    val targetMember: String,
    val targetAccount: Long,
    val money: Long,
    val spendType: SpendType,
    val balance: Long,
    val createdDate: String,
)

enum class SpendType {
    INCOME, EXPENDITURE
}
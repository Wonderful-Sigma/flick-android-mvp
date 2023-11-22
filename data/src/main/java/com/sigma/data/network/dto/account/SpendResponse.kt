package com.sigma.data.network.dto.account


data class SpendResponse(
    val targetMember: String,
    val targetAccount: Long,
    val money: Long,
    val spendType: String,
    val balance: Long,
    val createdDate: String,
)
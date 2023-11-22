package com.sigma.data.network.dto.account

data class AccountObject (
    val id: Long,
    val number: String,
    val name: String,
    val money: Long,
    val managerId: String,
    val accountType: String,
    val fileUrl: String?
)
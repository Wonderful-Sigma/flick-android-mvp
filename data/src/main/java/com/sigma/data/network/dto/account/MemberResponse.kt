package com.sigma.data.network.dto.account


data class MemberResponseDto(
    val id: String,
    val studentNumber: String,
    val name: String,
    val firebaseToken: String,
    val memberRule: String,
    val accounts: List<Account>
)

data class Account(
    val id: Long,
    val number: String,
    val name: String,
    val money: Long,
    val managerId: String,
    val accountType: String,
    val fileUrl: String
)
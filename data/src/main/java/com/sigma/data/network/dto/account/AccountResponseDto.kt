package com.sigma.data.network.dto.account

import com.sigma.main.model.account.Account

data class AccountResponseDto(
    val id: Long,
    val number: String,
    val name: String,
    val money: Long,
    val managerId: String,
    val accountType: String,
    val spendLists: List<Account>
)

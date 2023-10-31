package com.sigma.data.network.dto.account

import com.sigma.main.model.account.SpendResponseModel

data class CreateWalletResponseDto (
    val id: Long,
    val number: String,
    val name: String,
    val money: Long,
    val managerId: String,
    val accountType: String,
    val accountNumber: Long,
    val spendLists: List<SpendResponseModel>
)
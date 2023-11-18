package com.sigma.data.network.dto.account


data class WalletResponse(
    val id: Long,
    val number: String,
    val name: String,
    val money: Long,
    val managerId: String,
    val accountType: String,
    val fileUrl: String?,
    val memberList: List<String>
)
   // val spendLists: List<Account>

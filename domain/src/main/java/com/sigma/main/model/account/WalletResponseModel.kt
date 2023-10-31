package com.sigma.main.model.account

data class WalletResponseModel (
    val id: Long,
    val number: String,
    val name: String,
    val money: Long,
    val managerId: String,
    val accountType: String,
    val fileUrl: String?,
    val memberList: List<String>
)
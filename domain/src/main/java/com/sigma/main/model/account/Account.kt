package com.sigma.main.model.account

data class Account(
    val id: Long,
    val number: String,
    val name: String,
    val money: Long,
    val managerId: String,
    val accountType: String,
    val fileUrl: String?
)

enum class AccountType {
    PERSONAL,
    PUBLIC
}
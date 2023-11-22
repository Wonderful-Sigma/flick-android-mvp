package com.sigma.data.network.dto.user

import com.sigma.data.network.dto.account.Account


data class UserResponse (
    val id: String,
    val studentNumber: String,
    val name: String,
    val firebaseToken: String?,
    val memberRule: String,
    val account: List<Account>,
)
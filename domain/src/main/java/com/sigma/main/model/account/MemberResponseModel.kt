package com.sigma.main.model.account

data class MemberResponseModel(
    val id: String,
    val studentNumber: String,
    val name: String,
    val firebaseToken: String,
    val memberRule: String,
    val accounts: List<Account>
)
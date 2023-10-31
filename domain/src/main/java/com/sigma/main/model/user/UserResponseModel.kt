package com.sigma.main.model.user

import com.sigma.main.model.account.Account

data class UserResponseModel(
    val id: String,
    val studentNumber: String,
    val name: String,
    val firebaseToken: String?,
    val memberRule: MemberRule,
    val account: List<Account>,
)

enum class MemberRule {
    STUDENT,
    MANAGER,
    TEACHER
}
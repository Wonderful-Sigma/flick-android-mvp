package com.sigma.data.network.dto.user

import com.sigma.main.model.account.Account
import com.sigma.main.model.user.MemberRule

data class UserResponseDto (
    val id: String,
    val studentNumber: String,
    val name: String,
    val firebaseToken: String?,
    val memberRule: MemberRule,
    val account: List<Account>,
)
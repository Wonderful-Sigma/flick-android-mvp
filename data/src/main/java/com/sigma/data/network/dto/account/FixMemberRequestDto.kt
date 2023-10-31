package com.sigma.data.network.dto.account

data class FixMemberRequestDto (
    val triggerMember: String,
    val targetMember: String
)
package com.sigma.data.network.dto.account

data class FixMemberRequest (
    val triggerMember: String,
    val targetMember: String
)
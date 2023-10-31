package com.sigma.data.network.dto.account

data class EventRequestDto (
    val targetMember: String,
    val money: Int
)
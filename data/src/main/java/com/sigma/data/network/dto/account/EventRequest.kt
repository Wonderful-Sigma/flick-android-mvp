package com.sigma.data.network.dto.account

data class EventRequest (
    val targetMember: String,
    val money: Int
)
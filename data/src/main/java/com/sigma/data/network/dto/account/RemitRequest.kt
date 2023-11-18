package com.sigma.data.network.dto.account

data class RemitRequest (
    val remittanceAccount : Long,
    val money : Long,
    val depositAccount : Long
)
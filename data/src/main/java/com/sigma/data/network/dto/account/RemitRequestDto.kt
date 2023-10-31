package com.sigma.data.network.dto.account

data class RemitRequestDto (
    val remittanceAccount : Long,
    val money : Long,
    val depositAccount : Long
)
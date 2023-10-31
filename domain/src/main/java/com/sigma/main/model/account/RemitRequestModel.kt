package com.sigma.main.model.account

data class RemitRequestModel(
    val remittanceAccount : Long,
    val money : Long,
    val depositAccount : Long
)
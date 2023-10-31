package com.sigma.data.network.dto.account

import java.time.Instant

data class SpendCalculateRequestDto(
    val searchDate: Instant,
    val accountId: Long
)

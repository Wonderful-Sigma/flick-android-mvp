package com.sigma.main.model.account

import java.time.Instant

data class SpendCalculateRequestModel (
    val searchDate: Instant,
    val accountId: Long
)
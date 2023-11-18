package com.sigma.data.network.dto.account

import java.time.Instant

data class CheckAlarm(
    val createdDate: Instant,
    val id: Long,
    val memberId: String,
    val body: String,
    val title: String
)

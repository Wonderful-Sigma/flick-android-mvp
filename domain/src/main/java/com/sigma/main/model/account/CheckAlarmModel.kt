package com.sigma.main.model.account

import java.time.Instant

data class CheckAlarmModel (
    val createdDate: Instant,
    val id: Long,
    val memberId: String,
    val body: String,
    val title: String
)
package com.sigma.flick.feature.send.state

data class AccountNumberState(
    val isSuccess: Boolean = false,
    val error: String = ""
)
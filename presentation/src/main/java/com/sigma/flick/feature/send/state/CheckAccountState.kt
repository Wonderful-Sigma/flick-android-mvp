package com.sigma.flick.feature.send.state

data class CheckAccountState(
    val isSuccess: Boolean = false,
    val error: String = ""
)
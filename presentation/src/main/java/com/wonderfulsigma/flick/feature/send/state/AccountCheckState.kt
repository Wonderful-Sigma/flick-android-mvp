package com.wonderfulsigma.flick.feature.send.state

data class AccountCheckState(
    val isSuccess: Boolean = false,
    val error: String = ""
)
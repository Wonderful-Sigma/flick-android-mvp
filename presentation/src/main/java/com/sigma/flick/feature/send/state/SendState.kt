package com.sigma.flick.feature.send.state

data class SendState(
    val isSuccess: Boolean = false,
    val error: String = ""
)
package com.wonderfulsigma.flick.feature.send.state

data class SendState(
    val isSuccess: Boolean = false,
    val error: String = ""
)
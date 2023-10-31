package com.sigma.flick.feature.collection.state

data class PaymentState(
    val isSuccess: Boolean = false,
    val error: String = ""
)

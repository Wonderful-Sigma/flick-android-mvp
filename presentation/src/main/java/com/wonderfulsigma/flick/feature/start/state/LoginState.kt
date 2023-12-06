package com.wonderfulsigma.flick.feature.start.state

import retrofit2.http.Body

data class LoginState(
    val isSuccess: Boolean = false,
    val error: String = ""
)
package com.wonderfulsigma.flick.feature.start.state

import retrofit2.http.Body

data class DauthLoginState(
    val isSuccess: Boolean = false,
    val error: String = ""
)
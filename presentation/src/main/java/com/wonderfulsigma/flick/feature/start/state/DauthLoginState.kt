package com.wonderfulsigma.flick.feature.start.state

import retrofit2.http.Body

data class DauthLoginState(
    val isSuccess: String = "",
    val error: String = ""
)
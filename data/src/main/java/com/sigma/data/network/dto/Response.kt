package com.sigma.data.network.dto

data class Response<T>(
    val status: Int,
    val message: String,
    val data: T
)
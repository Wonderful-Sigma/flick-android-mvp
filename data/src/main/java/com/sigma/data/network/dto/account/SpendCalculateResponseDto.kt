package com.sigma.data.network.dto.account

data class SpendCalculateResponseDto(
    val allList: List<List<SpendResponseDto>>,
    val incomeList: List<List<SpendResponseDto>>,
    val expenditureList: List<List<SpendResponseDto>>,
    val loss: Long,
    val benefit: Long
)

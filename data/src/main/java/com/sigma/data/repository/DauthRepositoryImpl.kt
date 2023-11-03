package com.sigma.data.repository

import com.sigma.data.mapper.toDto
import com.sigma.data.mapper.toModel
import com.sigma.data.network.api.DauthApi
import com.sigma.main.model.dauth.DauthResponseModel
import com.sigma.main.model.dauth.DauthRequestModel
import com.sigma.main.repository.DauthRepository
import javax.inject.Inject

class DauthRepositoryImpl @Inject constructor(
    private val dauthApi: DauthApi
) : DauthRepository {

    override suspend fun login(dauthRequestDto: DauthRequestModel): DauthResponseModel =
        dauthApi.login(dauthRequestDto.toDto()).toModel()

}
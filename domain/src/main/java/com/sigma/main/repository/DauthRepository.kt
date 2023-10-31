package com.sigma.main.repository

import com.sigma.main.model.dauth.DauthResponseModel
import com.sigma.main.model.dauth.DauthRequestModel

interface DauthRepository {

    suspend fun login(
        dauthRequestDto: DauthRequestModel
    ): DauthResponseModel

}
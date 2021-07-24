package com.alfonso.service.imp

import com.alfonso.model.database.ApiKeyDB
import com.alfonso.repository.IRepositoryApiKey
import com.alfonso.response.AuthServiceResponse
import com.alfonso.service.ApiKeyService
import com.alfonso.service.AuthService

class AuthServiceImp(private val repositoryApiKey : IRepositoryApiKey,private val apiService : ApiKeyService) : AuthService {

    override suspend fun addApiKey(user: String) : AuthServiceResponse {
        val apiKey = apiService.generateApiKey()
        val apiDB = repositoryApiKey.insertApiKey(user,apiKey)
        return if(apiDB == null) AuthServiceResponse.UnexpectedError
                else AuthServiceResponse.Success(apiDB)
    }

    override suspend fun validateUserPass(user: String, api: String): AuthServiceResponse {
        val apiKey = repositoryApiKey.getApiKey(user,api)
        return if(apiKey != null) AuthServiceResponse.Success(ApiKeyDB(null,"","")) else AuthServiceResponse.UnexpectedError
    }
}
package com.alfonso.apikey.service.imp

import com.alfonso.apikey.model.database.ApiKeyDB
import com.alfonso.apikey.repository.IRepositoryApiKey
import com.alfonso.apikey.response.AuthServiceResponse
import com.alfonso.apikey.service.ApiKeyService
import com.alfonso.apikey.service.AuthService

class AuthServiceImp(private val repositoryApiKey : IRepositoryApiKey, private val apiService : ApiKeyService) :
    AuthService {

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
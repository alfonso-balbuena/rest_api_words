package com.alfonso.service.imp

import com.alfonso.model.ServiceResponse
import com.alfonso.model.database.ApiKeyDB
import com.alfonso.repository.IRepositoryApiKey
import com.alfonso.service.ApiKeyService
import com.alfonso.service.AuthService

class AuthServiceImp(private val repositoryApiKey : IRepositoryApiKey,private val apiService : ApiKeyService) : AuthService {

    override fun addApiKey(user: String) : ServiceResponse<ApiKeyDB> {
        val apiKey = apiService.generateApiKey()
        val apiDB = repositoryApiKey.insertApiKey(user,apiKey)
        return if(apiDB == null) ServiceResponse.Error(1,"UnexpectedError")
                else ServiceResponse.Success(apiDB)
    }

    override fun validateUserPass(user: String, api: String): ServiceResponse<Boolean> {
        val apiKey = repositoryApiKey.getApiKey(user,api)
        return if(apiKey != null) ServiceResponse.Success(true) else ServiceResponse.Error(1,"Unexpected")
    }


}
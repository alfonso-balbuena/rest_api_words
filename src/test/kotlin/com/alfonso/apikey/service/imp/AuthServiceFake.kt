package com.alfonso.apikey.service.imp

import com.alfonso.apikey.model.database.ApiKeyDB
import com.alfonso.apikey.model.database.getTestApiKeyDB
import com.alfonso.apikey.response.AuthServiceResponse
import com.alfonso.apikey.service.AuthService

class AuthServiceFake : AuthService {
    var hasError = false

    override suspend fun addApiKey(user: String): AuthServiceResponse {
        return if(hasError) AuthServiceResponse.UnexpectedError
            else AuthServiceResponse.Success(ApiKeyDB.getTestApiKeyDB())
    }

    override suspend fun validateUserPass(user: String, api: String): AuthServiceResponse {
        print("*** Validating user pass ***")
        return if(hasError) AuthServiceResponse.UnexpectedError
        else AuthServiceResponse.Success(ApiKeyDB.getTestApiKeyDB())
    }
}
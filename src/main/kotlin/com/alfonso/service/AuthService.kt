package com.alfonso.service

import com.alfonso.model.ServiceResponse
import com.alfonso.model.database.ApiKeyDB

interface AuthService {
    suspend fun addApiKey(user: String) : ServiceResponse<ApiKeyDB>
    suspend fun validateUserPass(user : String, api : String) : ServiceResponse<Boolean>
}
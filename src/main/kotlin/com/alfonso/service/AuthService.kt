package com.alfonso.service

import com.alfonso.model.ServiceResponse
import com.alfonso.model.database.ApiKeyDB

interface AuthService {
    fun addApiKey(user: String) : ServiceResponse<ApiKeyDB>
    fun validateUserPass(user : String, api : String) : ServiceResponse<Boolean>
}
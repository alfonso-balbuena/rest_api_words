package com.alfonso.apikey.service

import com.alfonso.apikey.response.AuthServiceResponse

interface AuthService {
    suspend fun addApiKey(user: String) : AuthServiceResponse
    suspend fun validateUserPass(user : String, api : String) : AuthServiceResponse
}
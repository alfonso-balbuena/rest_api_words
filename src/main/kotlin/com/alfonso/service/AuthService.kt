package com.alfonso.service

import com.alfonso.response.AuthServiceResponse

interface AuthService {
    suspend fun addApiKey(user: String) : AuthServiceResponse
    suspend fun validateUserPass(user : String, api : String) : AuthServiceResponse
}
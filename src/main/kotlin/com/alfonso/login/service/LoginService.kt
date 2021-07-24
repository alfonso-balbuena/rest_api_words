package com.alfonso.login.service

import com.alfonso.general.model.request.AuthRequest
import com.alfonso.login.model.request.LoginRequest
import com.alfonso.login.model.request.UserAddRequest

interface LoginService {
    suspend fun login(dataLogin : LoginRequest) : LoginServiceResponse
    suspend fun register(userData: UserAddRequest) : LoginServiceResponse
    suspend fun logout(data: AuthRequest) : LoginServiceResponse
}
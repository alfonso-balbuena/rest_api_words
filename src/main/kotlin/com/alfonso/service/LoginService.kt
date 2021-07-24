package com.alfonso.service

import com.alfonso.model.ServiceResponse
import com.alfonso.model.request.AuthRequest
import com.alfonso.model.request.LoginRequest
import com.alfonso.model.request.UserAddRequest
import com.alfonso.model.response.UserResponse

interface LoginService {
    fun login(dataLogin : LoginRequest) : ServiceResponse<UserResponse>
    fun register(userData: UserAddRequest) : ServiceResponse<UserResponse>
    fun logout(data: AuthRequest) : ServiceResponse<Boolean>
}
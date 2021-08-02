package com.alfonso.tokeauth.service

import com.alfonso.general.model.request.AuthRequest

interface TokenAuthService {
    suspend fun isTokenAuth(authData: AuthRequest): TokenAuthServiceResponse
}
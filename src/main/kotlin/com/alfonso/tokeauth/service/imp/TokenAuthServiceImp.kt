package com.alfonso.tokeauth.service.imp

import com.alfonso.general.model.request.AuthRequest
import com.alfonso.login.repository.IRepositoryUser
import com.alfonso.tokeauth.service.TokenAuthService
import com.alfonso.tokeauth.service.TokenAuthServiceResponse

class TokenAuthServiceImp(private val repositoryUser: IRepositoryUser) : TokenAuthService {

    override suspend fun isTokenAuth(authData : AuthRequest): TokenAuthServiceResponse {
        val validation = authData.validateAuthRequest()
        if(validation.isNotEmpty())
            return TokenAuthServiceResponse.FieldMissing(validation)
        val user = repositoryUser.getUser(authData.id) ?: return TokenAuthServiceResponse.NoUserError
        user.tokens.find { it.token == authData.token } ?: return TokenAuthServiceResponse.TokenNoValid
        return TokenAuthServiceResponse.Success
    }
}
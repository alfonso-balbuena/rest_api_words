package com.alfonso.login.service.imp

import com.alfonso.login.model.database.UserDB
import com.alfonso.general.model.request.AuthRequest
import com.alfonso.login.model.request.LoginRequest
import com.alfonso.login.model.request.UserAddRequest
import com.alfonso.login.model.response.UserResponse
import com.alfonso.login.repository.IRepositoryUser
import com.alfonso.login.service.LoginServiceResponse
import com.alfonso.login.service.LoginService
import com.alfonso.login.service.TokenService
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class LoginServiceImp(private val repositoryUser : IRepositoryUser, private val tokenService : TokenService) :
    LoginService {
    companion object {
        val logger : Logger = LoggerFactory.getLogger(LoginServiceImp::class.java)
    }
    override suspend fun login(dataLogin: LoginRequest): LoginServiceResponse {
        val validation = dataLogin.validateLoginRequest()
        if(validation.isNotEmpty())
            return LoginServiceResponse.FieldsMissingError(validation)
        val user = repositoryUser.getUser(dataLogin.id,dataLogin.provider)
        return if(user != null) {
            val newToken = tokenService.getToken()
            user.tokens.add(newToken)
            repositoryUser.updateUser(user)
            LoginServiceResponse.Success(UserResponse(user._id!!,user.email,user.name,user.imgPath,user.userId,user.provider,newToken.token))
        } else {
            LoginServiceResponse.NoUserError
        }
    }

    override suspend fun register(userData: UserAddRequest): LoginServiceResponse {
        val userValidate = repositoryUser.getUser(userData.userIdProvider,userData.provider)
        return if(userValidate == null) {
            val userDB = repositoryUser.insertUser(UserDB(null,userData.email,userData.name,userData.imgPath,userData.userIdProvider,userData.provider,ArrayList()))
            if(userDB != null)  login(LoginRequest(userData.userIdProvider,userData.provider))
            else LoginServiceResponse.UnexpectedError
        } else LoginServiceResponse.UserExistError
    }

    override suspend fun logout(data: AuthRequest): LoginServiceResponse {
        val user = repositoryUser.getUser(data.id)
        user?.let {
            it.tokens.removeIf { token -> token.token == data.token }
            val result = repositoryUser.updateUser(it)
            return if(result > 0) LoginServiceResponse.Success(UserResponse.emptyUserResponse())
                    else LoginServiceResponse.UnexpectedError
        }
        return LoginServiceResponse.NoUserError
    }
}
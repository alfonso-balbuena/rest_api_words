package com.alfonso.service.imp

import com.alfonso.model.ServiceResponse
import com.alfonso.model.database.TokenDB
import com.alfonso.model.database.UserDB
import com.alfonso.model.request.AuthRequest
import com.alfonso.model.request.LoginRequest
import com.alfonso.model.request.UserAddRequest
import com.alfonso.model.response.UserResponse
import com.alfonso.repository.IRepositoryUser
import com.alfonso.service.LoginService
import com.alfonso.service.TokenService

class LoginServiceImp(private val repositoryUser : IRepositoryUser,private val tokenService : TokenService) : LoginService {
    override fun login(dataLogin: LoginRequest): ServiceResponse<UserResponse> {
        val user = repositoryUser.getUser(dataLogin.id,dataLogin.provider)
        return if(user != null) {
            val newToken = tokenService.getToken()
            user.tokens.add(newToken)
            repositoryUser.updateUser(user)
            ServiceResponse.Success(UserResponse(user._id!!,user.email,user.name,user.imgPath,user.userId,user.provider,newToken.token))
        } else {
            ServiceResponse.Error(10,"No user found")
        }
    }

    override fun register(userData: UserAddRequest): ServiceResponse<UserResponse> {
        val userValidate = repositoryUser.getUser(userData.userIdProvider,userData.provider)
        return if(userValidate == null) {
            val userDB = repositoryUser.insertUser(UserDB(null,userData.email,userData.name,userData.imgPath,userData.userIdProvider,userData.provider,ArrayList<TokenDB>()))
            if(userDB != null)  login(LoginRequest(userData.userIdProvider,userData.provider))
            else ServiceResponse.Error(1,"Unexpected error")
        } else ServiceResponse.Error(11,"The user exists already")
    }

    override fun logout(data: AuthRequest): ServiceResponse<Boolean> {
        val user = repositoryUser.getUser(data.id)
        user?.let {
            it.tokens.removeIf { token -> token.token == data.token }
            val result = repositoryUser.updateUser(it)
            return if(result > 0) ServiceResponse.Success(true)
                    else ServiceResponse.Error(1,"Unexpected error")
        }
        return ServiceResponse.Error(10,"No user found")
    }
}
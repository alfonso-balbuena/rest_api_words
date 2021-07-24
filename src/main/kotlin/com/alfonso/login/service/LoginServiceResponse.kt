package com.alfonso.login.service

import com.alfonso.login.model.response.UserResponse

sealed class LoginServiceResponse {
    class Success(val value: UserResponse) : LoginServiceResponse()
    object NoUserError : LoginServiceResponse()
    object UserExistError : LoginServiceResponse()
    object UnexpectedError : LoginServiceResponse()
}

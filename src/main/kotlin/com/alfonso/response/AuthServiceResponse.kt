package com.alfonso.response

import com.alfonso.model.database.ApiKeyDB

sealed class AuthServiceResponse {
    class Success(val value: ApiKeyDB) : AuthServiceResponse()
    object UnexpectedError : AuthServiceResponse()
}

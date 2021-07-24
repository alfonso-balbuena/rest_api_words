package com.alfonso.apikey.response

import com.alfonso.apikey.model.database.ApiKeyDB

sealed class AuthServiceResponse {
    class Success(val value: ApiKeyDB) : AuthServiceResponse()
    object UnexpectedError : AuthServiceResponse()
}

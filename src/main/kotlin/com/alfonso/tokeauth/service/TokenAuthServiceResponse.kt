package com.alfonso.tokeauth.service

sealed class TokenAuthServiceResponse {
    object Success : TokenAuthServiceResponse()
    object NoUserError : TokenAuthServiceResponse()
    object TokenNoValid : TokenAuthServiceResponse()
    class FieldMissing(val fields : List<String>) : TokenAuthServiceResponse()
}

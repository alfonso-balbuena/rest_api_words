package com.alfonso.general.response

sealed class Response(val code: Int, val message: String) {
    object UnexpectedErrorResponse : Response(1,"Unexpected error")
    object Success : Response(0, "Success")
    class SuccessWithData<out T>(val data : T) : Response(0,"Success")
}
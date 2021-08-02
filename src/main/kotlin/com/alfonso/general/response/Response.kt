package com.alfonso.general.response

sealed class Response(val code: Int, val message: String) {
    object UnexpectedErrorResponse : Response(1,"Unexpected error")
    class FieldsMissingResponse(private val fields: List<String>) : Response(2, "The fields ${fields.joinToString(", ")} are required")
    object BodyErrorResponse : Response(3,"There are problems with the request body")
    object Success : Response(0, "Success")
    class SuccessWithData<out T>(val data : T) : Response(0,"Success")
}

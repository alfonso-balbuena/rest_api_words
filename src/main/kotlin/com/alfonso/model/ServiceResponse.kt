package com.alfonso.model

import kotlinx.serialization.Serializable

@Serializable
sealed class ServiceResponse<out T: Any> {
    class Success<out T : Any>(val data: T) : ServiceResponse<T>()
    class Error(val code: Int, val message: String) : ServiceResponse<Nothing>()
}


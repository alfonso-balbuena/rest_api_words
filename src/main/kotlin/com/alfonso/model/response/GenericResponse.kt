package com.alfonso.model.response

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class GenericResponse<out T>(val code : Int, val message: String,val data: T?)

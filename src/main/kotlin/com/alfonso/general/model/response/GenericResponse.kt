package com.alfonso.general.model.response

import kotlinx.serialization.Serializable

@Serializable
data class GenericResponse<out T>(val code : Int, val message: String,val data: T? = null)

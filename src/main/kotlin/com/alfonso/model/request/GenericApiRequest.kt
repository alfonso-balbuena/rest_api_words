package com.alfonso.model.request

import kotlinx.serialization.Serializable

@Serializable
data class GenericApiRequest<out T>(val auth: AuthRequest, val data : T? = null)

package com.alfonso.model.request

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(val id: String, val provider: String)

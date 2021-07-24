package com.alfonso.login.model.request

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(val id: String, val provider: String)

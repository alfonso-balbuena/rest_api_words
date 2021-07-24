package com.alfonso.general.model.request

import kotlinx.serialization.Serializable

@Serializable
data class AuthRequest(val id: String, val token: String)

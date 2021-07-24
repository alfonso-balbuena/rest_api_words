package com.alfonso.model.request

import kotlinx.serialization.Serializable

@Serializable
data class ApiKeyRequest(val user: String)

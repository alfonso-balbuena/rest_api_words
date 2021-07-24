package com.alfonso.model.response

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(val _id : String, val email: String, val provider : String, val fullName : String, val impPath: String, val token: String)

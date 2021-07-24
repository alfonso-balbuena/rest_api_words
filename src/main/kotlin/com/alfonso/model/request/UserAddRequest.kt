package com.alfonso.model.request

import kotlinx.serialization.Serializable

@Serializable
data class UserAddRequest(val provider: String, val email: String, val name: String,val imgPath : String, val userIdProvider : String) {
}
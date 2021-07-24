package com.alfonso.model.response

import com.alfonso.model.database.TokenDB
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(val _id : String, val email: String, val name: String, val imgPath: String, val userId : String,val provider: String, val token : String)
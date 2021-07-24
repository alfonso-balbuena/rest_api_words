package com.alfonso.login.model.response

import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(val _id : String, val email: String, val name: String, val imgPath: String, val userId : String,val provider: String, val token : String) {
    companion object {
        fun emptyUserResponse() : UserResponse {
            return UserResponse("","","","","","","")
        }
    }
}

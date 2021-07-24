package com.alfonso.login.model.database

import com.alfonso.login.model.response.UserResponse

fun UserResponse.Companion.getTestObject() : UserResponse {
    return UserResponse("1","test@test","test",imgPath = "",userId = "1",provider = "google","token")
}
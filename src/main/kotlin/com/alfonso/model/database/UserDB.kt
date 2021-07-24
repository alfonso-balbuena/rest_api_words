package com.alfonso.model.database

import kotlinx.serialization.Serializable

@Serializable
data class UserDB(var _id : String?, val email: String, val name: String, val imgPath: String, val userId : String,val provider: String, val tokens : ArrayList<TokenDB> ) {
}
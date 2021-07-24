package com.alfonso.apikey.model.database

import kotlinx.serialization.Serializable

@Serializable
data class ApiKeyDB(var _id : String?,val api_key : String,val user : String)
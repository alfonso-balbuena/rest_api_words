package com.alfonso.apikey.model.database

fun ApiKeyDB.Companion.getTestApiKeyDB() : ApiKeyDB {
    return ApiKeyDB("1","api","user")
}
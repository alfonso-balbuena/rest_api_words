package com.alfonso.apikey.repository

import com.alfonso.apikey.model.database.ApiKeyDB

interface IRepositoryApiKey {
    suspend fun getApiKey(user: String, api_key: String) : ApiKeyDB?
    suspend fun insertApiKey(user: String, api_key: String) : ApiKeyDB?
}
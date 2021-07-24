package com.alfonso.repository

import com.alfonso.model.database.ApiKeyDB

interface IRepositoryApiKey {
    fun getApiKey(user: String, api_key: String) : ApiKeyDB?
    fun insertApiKey(user: String, api_key: String) : ApiKeyDB?
}
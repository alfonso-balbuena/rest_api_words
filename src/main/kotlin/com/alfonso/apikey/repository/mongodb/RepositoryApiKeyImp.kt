package com.alfonso.apikey.repository.mongodb

import com.alfonso.apikey.model.database.ApiKeyDB
import com.alfonso.apikey.repository.IRepositoryApiKey
import io.ktor.utils.io.*
import org.litote.kmongo.eq
import org.litote.kmongo.and
import org.litote.kmongo.coroutine.CoroutineDatabase
import java.lang.Exception

class RepositoryApiKeyImp(private val dataBase : CoroutineDatabase) : IRepositoryApiKey {
    private val API_KEY_COLLECTION = "api_keys"

    override suspend fun getApiKey(user: String, api_key: String): ApiKeyDB? {
        val collection = dataBase.getCollection<ApiKeyDB>(API_KEY_COLLECTION)
        return collection.findOne(and(ApiKeyDB::api_key eq api_key, ApiKeyDB::user eq user))
    }

    override suspend fun insertApiKey(user: String, api_key: String) : ApiKeyDB? {
        val collection = dataBase.getCollection<ApiKeyDB>(API_KEY_COLLECTION)
        val apiDB = ApiKeyDB(null,api_key,user)
        return try {
            collection.insertOne(apiDB)
            apiDB
        } catch (ex: Exception) {
            ex.printStack()
            null
        }
    }
}
package com.alfonso.general.repository.mongodb

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import org.litote.kmongo.coroutine.*
import org.litote.kmongo.reactivestreams.KMongo

object MongoDB {
    fun getDataBase(connection: String, databaseString : String) : CoroutineDatabase {
        val connectionString = ConnectionString(connection)
        val settings = MongoClientSettings.builder().applyConnectionString(connectionString).build()
        val mongoClient = KMongo.createClient(settings).coroutine
        return mongoClient.getDatabase(databaseString)
    }
}
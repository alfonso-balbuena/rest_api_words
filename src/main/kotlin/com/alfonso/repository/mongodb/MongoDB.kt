package com.alfonso.repository.mongodb

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.client.MongoDatabase
import org.litote.kmongo.*

object MongoDB {
    fun getDataBase(connection: String, databaseString : String) : MongoDatabase {
        val connectionString = ConnectionString(connection)
        val settings = MongoClientSettings.builder().applyConnectionString(connectionString).build()
        val mongoClient = KMongo.createClient(settings)
        return mongoClient.getDatabase(databaseString)
    }
}
package com.alfonso.repository.mongodb

import com.alfonso.model.database.UserDB
import com.alfonso.repository.IRepositoryUser
import com.mongodb.client.MongoDatabase
import io.ktor.utils.io.*
import org.litote.kmongo.and
import org.litote.kmongo.eq
import org.litote.kmongo.findOne
import org.litote.kmongo.getCollection
import java.lang.Exception


class RepositoryUserImp(private val dataBase : MongoDatabase) : IRepositoryUser {
    private val USER_COLLECTION = "users"

    override fun getUser(userId: String, provider: String): UserDB? {
        val collection = dataBase.getCollection<UserDB>(USER_COLLECTION)
        return collection.findOne(and(UserDB::provider eq provider,UserDB::userId eq userId ))
    }

    override fun getUser(_id: String): UserDB? {
        val collection = dataBase.getCollection<UserDB>(USER_COLLECTION)
        return collection.findOne(UserDB::_id eq _id)
    }

    override fun updateUser(user: UserDB) : Int {
        val collection = dataBase.getCollection<UserDB>(USER_COLLECTION)
        val result = collection.replaceOne(UserDB::_id eq user._id,user)
        return result.modifiedCount.toInt()
    }

    override fun insertUser(user: UserDB) : UserDB? {
        return try {
            val collection = dataBase.getCollection<UserDB>(USER_COLLECTION)
            collection.insertOne(user)
            user
        } catch (ex: Exception) {
            ex.printStack()
            null
        }
    }

}
package com.alfonso.login.repository.mongodb

import com.alfonso.login.model.database.UserDB
import com.alfonso.login.repository.IRepositoryUser
import io.ktor.utils.io.*
import org.litote.kmongo.and
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
import java.lang.Exception


class RepositoryUserImp(private val dataBase : CoroutineDatabase) : IRepositoryUser {
    private val USER_COLLECTION = "users"

    override suspend fun getUser(userId: String, provider: String): UserDB? {
        val collection = dataBase.getCollection<UserDB>(USER_COLLECTION)
        return collection.findOne(and(UserDB::provider eq provider, UserDB::userId eq userId ))
    }

    override suspend fun getUser(_id: String): UserDB? {
        val collection = dataBase.getCollection<UserDB>(USER_COLLECTION)
        return collection.findOne(UserDB::_id eq _id)
    }

    override suspend fun updateUser(user: UserDB) : Int {
        val collection = dataBase.getCollection<UserDB>(USER_COLLECTION)
        val result = collection.replaceOne(UserDB::_id eq user._id,user)
        return result.modifiedCount.toInt()
    }

    override suspend fun insertUser(user: UserDB) : UserDB? {
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
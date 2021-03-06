package com.alfonso.login.repository.fake

import com.alfonso.login.model.database.UserDB
import com.alfonso.login.model.database.getTestUserDB
import com.alfonso.login.repository.IRepositoryUser

class RepositoryUserFake : IRepositoryUser {
    var isReturningNull : Boolean = false
    var isGetUserProviderNull : Boolean = false
    var isGetUserIdNull : Boolean = false
    var isUpdateUserNoUpdate : Boolean = false
    var isInsertUserNull : Boolean = false

    override suspend fun getUser(userId: String, provider: String): UserDB? {
        return if(isReturningNull || isGetUserProviderNull) null else
            return UserDB.getTestUserDB()
    }

    override suspend fun getUser(_id: String): UserDB? {
        return if(isReturningNull || isGetUserIdNull) null else
            return UserDB.getTestUserDB()
    }

    override suspend fun updateUser(user: UserDB): Int {
        return if(isReturningNull || isUpdateUserNoUpdate) 0 else
            return 1
    }

    override suspend fun insertUser(user: UserDB): UserDB? {
        return if(isReturningNull || isInsertUserNull) null else
            return UserDB.getTestUserDB()
    }
}
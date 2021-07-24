package com.alfonso.login.repository.fake

import com.alfonso.login.model.database.UserDB
import com.alfonso.login.model.database.getTestUserDB
import com.alfonso.login.repository.IRepositoryUser

class RepositoryUserFake : IRepositoryUser {
    var hasError : Boolean = false

    override suspend fun getUser(userId: String, provider: String): UserDB? {
        return if(hasError) null else
            return UserDB.getTestUserDB()
    }

    override suspend fun getUser(_id: String): UserDB? {
        return if(hasError) null else
            return UserDB.getTestUserDB()
    }

    override suspend fun updateUser(user: UserDB): Int {
        return if(hasError) 0 else
            return 1
    }

    override suspend fun insertUser(user: UserDB): UserDB? {
        return if(hasError) null else
            return UserDB.getTestUserDB()
    }
}
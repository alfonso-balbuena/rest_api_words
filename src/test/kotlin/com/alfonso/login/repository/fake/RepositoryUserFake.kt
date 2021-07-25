package com.alfonso.login.repository.fake

import com.alfonso.login.model.database.UserDB
import com.alfonso.login.model.database.getTestUserDB
import com.alfonso.login.repository.IRepositoryUser

class RepositoryUserFake : IRepositoryUser {
    var isReturningNull : Boolean = false

    override suspend fun getUser(userId: String, provider: String): UserDB? {
        return if(isReturningNull) null else
            return UserDB.getTestUserDB()
    }

    override suspend fun getUser(_id: String): UserDB? {
        return if(isReturningNull) null else
            return UserDB.getTestUserDB()
    }

    override suspend fun updateUser(user: UserDB): Int {
        return if(isReturningNull) 0 else
            return 1
    }

    override suspend fun insertUser(user: UserDB): UserDB? {
        return if(isReturningNull) null else
            return UserDB.getTestUserDB()
    }
}
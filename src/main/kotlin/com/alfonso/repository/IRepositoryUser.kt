package com.alfonso.repository

import com.alfonso.model.database.UserDB

interface IRepositoryUser {
    suspend fun getUser(userId : String, provider : String) : UserDB?
    suspend fun getUser(_id : String) : UserDB?
    suspend fun updateUser(user : UserDB) : Int
    suspend fun insertUser(user: UserDB) : UserDB?
}
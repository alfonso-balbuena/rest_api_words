package com.alfonso.repository

import com.alfonso.model.database.UserDB

interface IRepositoryUser {
    fun getUser(userId : String,provider : String) : UserDB?
    fun getUser(_id : String) : UserDB?
    fun updateUser(user : UserDB) : Int
    fun insertUser(user: UserDB) : UserDB?
}
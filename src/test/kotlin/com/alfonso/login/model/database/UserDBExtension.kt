package com.alfonso.login.model.database

fun UserDB.Companion.getTestUserDB() : UserDB {
    return UserDB("1","test@test","test","","1","google",ArrayList())
}
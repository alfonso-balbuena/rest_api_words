package com.alfonso.login.service

import com.alfonso.login.model.database.TokenDB

interface TokenService {
    fun getToken() : TokenDB
}
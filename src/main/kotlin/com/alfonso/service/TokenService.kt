package com.alfonso.service

import com.alfonso.model.database.TokenDB

interface TokenService {
    fun getToken() : TokenDB
}
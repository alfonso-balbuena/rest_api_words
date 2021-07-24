package com.alfonso.login.service.imp

import com.alfonso.login.model.database.TokenDB
import com.alfonso.login.service.TokenService
import java.util.*

class TokenServiceFake : TokenService {
    override fun getToken(): TokenDB {
        return TokenDB("token", Date())
    }
}
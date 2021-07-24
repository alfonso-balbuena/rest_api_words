package com.alfonso.login.service.imp

import com.alfonso.login.model.database.TokenDB
import com.alfonso.login.service.TokenService
import java.text.SimpleDateFormat
import java.util.*

class TokenServiceImp : TokenService {
    override fun getToken(): TokenDB {
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        val date = Date()
        val currentDate = sdf.format(date)
        return TokenDB(Base64.getEncoder().encodeToString(currentDate.encodeToByteArray()),date)
    }
}
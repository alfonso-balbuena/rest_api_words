package com.alfonso.service.imp

import com.alfonso.model.database.TokenDB
import com.alfonso.service.TokenService
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
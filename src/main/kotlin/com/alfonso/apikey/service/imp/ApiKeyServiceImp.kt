package com.alfonso.apikey.service.imp

import com.alfonso.apikey.service.ApiKeyService
import java.util.*
import kotlin.random.Random

class ApiKeyServiceImp : ApiKeyService {

    override fun generateApiKey(): String {
        val key = Random.Default.nextBytes(32)
        return Base64.getEncoder().encodeToString(key)
    }
}
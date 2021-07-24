package com.alfonso.routing

import com.alfonso.model.ServiceResponse
import com.alfonso.model.database.ApiKeyDB
import com.alfonso.model.request.ApiKeyRequest
import com.alfonso.model.response.GenericResponse
import com.alfonso.model.response.NothingResponse
import com.alfonso.service.AuthService
import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Route.apiKeyRouting() {
    val authService : AuthService by inject()
    route("/generate") {
        post {
            val user = call.receive<ApiKeyRequest>()
            when (val result = authService.addApiKey(user.user)) {
                is ServiceResponse.Success<ApiKeyDB> ->  {
                    val apiResponse = result.data
                    call.respond(GenericResponse(0, "", apiResponse))
                }
                is ServiceResponse.Error -> {
                    call.respond(GenericResponse(result.code, result.message, NothingResponse()))
                }
            }

        }
    }
}
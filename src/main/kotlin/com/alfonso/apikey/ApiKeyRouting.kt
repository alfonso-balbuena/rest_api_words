package com.alfonso.apikey

import com.alfonso.apikey.model.request.ApiKeyRequest
import com.alfonso.apikey.response.AuthServiceResponse
import com.alfonso.general.response.GeneralResponse
import com.alfonso.apikey.service.AuthService
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
                is AuthServiceResponse.Success ->  {
                    val apiResponse = result.value
                    call.respond(GeneralResponse.getSuccessWithData(apiResponse))
                }
                is AuthServiceResponse.UnexpectedError -> {
                    call.respond(GeneralResponse.getUnexpectedErrorResponse())
                }
            }

        }
    }
}
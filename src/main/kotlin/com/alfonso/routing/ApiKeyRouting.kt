package com.alfonso.routing

import com.alfonso.model.request.ApiKeyRequest
import com.alfonso.model.response.GenericResponse
import com.alfonso.model.response.NothingResponse
import com.alfonso.response.AuthServiceResponse
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
                is AuthServiceResponse.Success ->  {
                    val apiResponse = result.value
                    call.respond(GenericResponse(0, "", apiResponse))
                }
                is AuthServiceResponse.UnexpectedError -> {
                    call.respond(GenericResponse(1, "Unexpected error", NothingResponse()))
                }
            }

        }
    }
}
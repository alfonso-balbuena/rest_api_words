package com.alfonso.plugins

import com.alfonso.model.ServiceResponse
import com.alfonso.service.AuthService
import io.ktor.auth.*
import io.ktor.util.*
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Application.configureSecurity() {
    val authService : AuthService by inject()
    install(Authentication) {
        basic("auth-basic") {
            realm = "Access to the '/' path "
            validate { credentials ->
                when(val result = authService.validateUserPass(credentials.name,credentials.password)) {
                    is ServiceResponse.Success<*> -> UserIdPrincipal(credentials.name)
                    else -> null
                }
            }
        }
    }
}

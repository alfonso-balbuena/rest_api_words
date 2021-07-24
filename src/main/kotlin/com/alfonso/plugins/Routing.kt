package com.alfonso.plugins

import com.alfonso.routing.userRouting
import com.alfonso.routing.wordTestRouting
import io.ktor.routing.*
import io.ktor.application.*
import io.ktor.auth.*
import com.alfonso.routing.loginRouting

fun Application.configureRouting() {

    routing {
        authenticate("auth-basic") {
            wordTestRouting()
            userRouting()
            loginRouting()
        }
    }
}

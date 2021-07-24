package com.alfonso.plugins

import com.alfonso.test.wordTestRouting
import io.ktor.routing.*
import io.ktor.application.*
import io.ktor.auth.*
import com.alfonso.login.loginRouting

fun Application.configureRouting() {

    routing {
        authenticate("auth-basic") {
            wordTestRouting()
            loginRouting()
        }
    }
}

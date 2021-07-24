package com.alfonso.plugins

import com.alfonso.test.wordTestRouting
import io.ktor.routing.*
import io.ktor.application.*
import io.ktor.auth.*
import com.alfonso.login.loginRouting

fun Application.configureRouting(testing: Boolean = false) {

    routing {
        if(!testing) {
            authenticate("auth-basic") {
                wordTestRouting()
                loginRouting()
            }
        } else {
            wordTestRouting()
            loginRouting()
        }
    }
}

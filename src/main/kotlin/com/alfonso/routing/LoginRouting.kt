package com.alfonso.routing

import com.alfonso.model.ServiceResponse
import com.alfonso.model.request.GenericApiRequest
import com.alfonso.model.request.LoginRequest
import com.alfonso.model.response.GenericResponse
import com.alfonso.model.response.NothingResponse
import com.alfonso.model.response.UserResponse
import com.alfonso.service.LoginService
import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Route.loginRouting() {
    val loginService: LoginService by inject()
    route("/login") {
        post {
            call.application.environment.log.info("Checking if user exist $call")
            val dataLogin = call.receive<LoginRequest>()
            when (val result = loginService.login(dataLogin)) {
                is ServiceResponse.Success<UserResponse> -> {
                    val userResponse = result.data
                    print(userResponse)
                    call.respond(userResponse)
                }
                is ServiceResponse.Error -> {
                    call.respond(GenericResponse(result.code, result.message, NothingResponse()))
                }
            }
        }
    }

    route("/logout") {
        post {
            call.application.environment.log.info("Logout user")
            val dataLogout = call.receive<GenericApiRequest<NothingResponse>>()
            when(val result = loginService.logout(dataLogout.auth)) {
                is ServiceResponse.Success<*> -> call.respond(GenericResponse(0,"",NothingResponse()))
                is ServiceResponse.Error -> call.respond(GenericResponse(result.code,result.message,NothingResponse()))
            }
        }
    }
}
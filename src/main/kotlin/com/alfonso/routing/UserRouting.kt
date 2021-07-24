package com.alfonso.routing

import com.alfonso.model.ServiceResponse
import com.alfonso.model.request.UserAddRequest
import com.alfonso.model.response.GenericResponse
import com.alfonso.model.response.NothingResponse
import com.alfonso.model.response.UserResponse
import com.alfonso.service.LoginService
import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Route.userRouting() {
    val loginService : LoginService by inject()
    route("/user/register") {
        post {
            val user = call.receive<UserAddRequest>()
            call.application.environment.log.info("In adding user $call --- $user")
            when (val result = loginService.register(user)) {
                is ServiceResponse.Success<UserResponse> -> call.respond(GenericResponse(0,"",result.data))
                is ServiceResponse.Error -> call.respond(GenericResponse(result.code,result.message,NothingResponse()))
            }
        }
    }
}
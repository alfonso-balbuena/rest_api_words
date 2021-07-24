package com.alfonso.login

import com.alfonso.general.model.request.GenericApiRequest
import com.alfonso.login.model.request.LoginRequest
import com.alfonso.login.model.request.UserAddRequest
import com.alfonso.general.model.response.NothingResponse
import com.alfonso.general.response.GeneralResponse
import com.alfonso.login.service.LoginService
import com.alfonso.login.service.LoginServiceResponse
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
                is LoginServiceResponse.Success -> {
                    val userResponse = result.value
                    print(userResponse)
                    call.respond(GeneralResponse.getSuccessWithData(userResponse))
                }
                is LoginServiceResponse.UnexpectedError -> call.respond(GeneralResponse.getUnexpectedErrorResponse())
                is LoginServiceResponse.NoUserError -> TODO()
                is LoginServiceResponse.UserExistError -> TODO()
            }
        }
    }

    route("/logout") {
        post {
            call.application.environment.log.info("Logout user")
            val dataLogout = call.receive<GenericApiRequest<NothingResponse>>()
            when(val result = loginService.logout(dataLogout.auth)) {
                is LoginServiceResponse.Success -> call.respond(GeneralResponse.getSuccess())
                is LoginServiceResponse.UnexpectedError -> call.respond(GeneralResponse.getUnexpectedErrorResponse())
                is LoginServiceResponse.NoUserError -> TODO()
                is LoginServiceResponse.UserExistError -> TODO()
            }
        }
    }

    route("/user/register") {
        post {
            val user = call.receive<UserAddRequest>()
            call.application.environment.log.info("In adding user $call --- $user")
            when (val result = loginService.register(user)) {
                is LoginServiceResponse.Success -> call.respond(GeneralResponse.getSuccessWithData(result.value))
                is LoginServiceResponse.UnexpectedError -> call.respond(GeneralResponse.getUnexpectedErrorResponse())
                is LoginServiceResponse.NoUserError -> TODO()
                is LoginServiceResponse.UserExistError -> TODO()
            }
        }
    }}
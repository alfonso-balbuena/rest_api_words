package com.alfonso.login

import com.alfonso.general.model.request.GenericApiRequest
import com.alfonso.login.model.request.LoginRequest
import com.alfonso.login.model.request.UserAddRequest
import com.alfonso.general.model.response.NothingResponse
import com.alfonso.general.response.GeneralResponse
import com.alfonso.login.service.LoginService
import com.alfonso.login.service.LoginServiceResponse
import com.alfonso.login.util.LoginResponseRest
import com.alfonso.tokeauth.GeneralValidation
import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.util.pipeline.*
import kotlinx.serialization.SerializationException
import kotlinx.serialization.internal.*

import org.koin.ktor.ext.inject
import kotlin.reflect.full.createType

fun Route.loginRouting() {
    val loginService: LoginService by inject()
    route("/login") {
        post {
            try {
                val dataLogin = call.receive<LoginRequest>()
                call.application.environment.log.info("$dataLogin")
                when (val result = loginService.login(dataLogin)) {
                    is LoginServiceResponse.Success -> call.respond(GeneralResponse.getSuccessWithData(result.value))
                    is LoginServiceResponse.UnexpectedError -> call.respond(GeneralResponse.getUnexpectedErrorResponse())
                    is LoginServiceResponse.FieldsMissingError -> call.respond(GeneralResponse.getFieldsErrorResponse(result.fields))
                    is LoginServiceResponse.NoUserError -> call.respond(LoginResponseRest.getNoUserErrorResponse())
                }
            }catch (ex: SerializationException) {
                call.respond(GeneralResponse.getBodyErrorResponse())
            }
        }
    }

    route("/logout") {
        post {
            GeneralValidation.routeTokenValidation(this) { pipeline: PipelineContext<Unit, ApplicationCall>, data: GenericApiRequest<NothingResponse> ->
                when (val result = loginService.logout(data.auth)) {
                    is LoginServiceResponse.Success -> call.respond(GeneralResponse.getSuccess())
                    is LoginServiceResponse.UnexpectedError -> call.respond(GeneralResponse.getUnexpectedErrorResponse())
                    is LoginServiceResponse.NoUserError -> call.respond(LoginResponseRest.getNoUserErrorResponse())
                }
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
                else -> loginGeneralErrorRespond(call, result)
            }
        }
    }
}

suspend fun loginGeneralErrorRespond(call: ApplicationCall, loginServiceResponse: LoginServiceResponse) {
    when (loginServiceResponse) {
        is LoginServiceResponse.NoUserError -> call.respond(LoginResponseRest.getNoUserErrorResponse())
        is LoginServiceResponse.UserExistError -> call.respond(LoginResponseRest.getUserExistErrorResponse())
        else -> call.respond("Error")
    }
}


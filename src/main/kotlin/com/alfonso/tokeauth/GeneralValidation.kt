package com.alfonso.tokeauth

import com.alfonso.general.model.request.GenericApiRequest
import com.alfonso.general.response.GeneralResponse
import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.util.pipeline.*
import kotlinx.serialization.SerializationException


class GeneralValidation {

    companion object {
        suspend inline fun <reified T> routeTokenValidation(
            pipelineContext: PipelineContext<Unit, ApplicationCall>,
            serviceResponse: (PipelineContext<Unit, ApplicationCall>, GenericApiRequest<T>) -> Unit) {
            try {
                val data = pipelineContext.call.receive<GenericApiRequest<T>>()
                serviceResponse(pipelineContext,data)
            }catch (ex: SerializationException) {
                if(ex.javaClass.toString().contains("JsonDecodingException"))
                    pipelineContext.call.respond(GeneralResponse.getBodyErrorResponse())
                else
                    ex.printStackTrace()
            }
        }
    }
}
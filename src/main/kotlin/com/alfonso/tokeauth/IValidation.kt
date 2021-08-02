package com.alfonso.tokeauth

import com.alfonso.general.model.request.GenericApiRequest
import io.ktor.application.*
import io.ktor.util.pipeline.*

interface IValidation {

    suspend fun <T> routeTokenValidation(pipelineContext: PipelineContext<Unit, ApplicationCall>,
                                                        serviceResponse: (PipelineContext<Unit, ApplicationCall>, GenericApiRequest<T>) -> Unit)
}
package com.alfonso.general.response

import com.alfonso.general.model.response.GenericResponse
import com.alfonso.general.model.response.NothingResponse
import com.alfonso.login.service.imp.LoginServiceImp
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class GeneralResponse {

    companion object {
        private val logger : Logger = LoggerFactory.getLogger(LoginServiceImp::class.java)

        fun getUnexpectedErrorResponse() : GenericResponse<NothingResponse> {
            return getGenericResponse(Response.UnexpectedErrorResponse)
        }

        fun getSuccess() : GenericResponse<NothingResponse> {
            return getGenericResponse(Response.Success)
        }

        fun <T> getSuccessWithData(data: T) : GenericResponse<T> {
            val successWithData = Response.SuccessWithData(data)
            return GenericResponse(successWithData.code,successWithData.message,data)
        }

        fun getFieldsErrorResponse(fields : List<String>) : GenericResponse<NothingResponse> {
            return getGenericResponse(Response.FieldsMissingResponse(fields))
        }

        fun getBodyErrorResponse() : GenericResponse<NothingResponse> {
            return getGenericResponse(Response.BodyErrorResponse)
        }

        private fun getGenericResponse(response: Response) : GenericResponse<NothingResponse> {
            return GenericResponse(response.code, response.message)
        }
    }
}